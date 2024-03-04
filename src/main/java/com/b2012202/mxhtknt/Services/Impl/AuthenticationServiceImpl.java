package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Controller.FileController;
import com.b2012202.mxhtknt.DTO.BaiVietDTO;
import com.b2012202.mxhtknt.DTO.NhaTroDTO;
import com.b2012202.mxhtknt.DTO.UserInfoDTO;
import com.b2012202.mxhtknt.Models.*;
import com.b2012202.mxhtknt.Repositories.*;
import com.b2012202.mxhtknt.Request.*;
import com.b2012202.mxhtknt.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final IStorageService iStorageService;
    private final BaiVietRepository baiVietRepository;
    private final NhaTroRepository nhaTroRepository;
    private final PhongRepository phongRepository;
    private final BaiVietServiceImpl baiVietServiceImpl;

    @Override
    public ResponseObject login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword())
            );
            User user = userService.findUserByUsername(loginRequest.getUsername());
            if (user != null) {
                var jwt = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
                return new ResponseObject("ok", "Login successfully",
                        JwtAuthenticationResponse.builder()
                                .token(jwt)
                                .refreshToken(refreshToken)
                                .user(user)
                                .build());
            } else {
                return new ResponseObject("Failed", "Username or password invalid", null);
            }
        } catch (Exception e) {
            return new ResponseObject("Failed", "Username or password invalid", null);
        }
    }

    @Override
    public ResponseObject signUp(SignUpRequest signUpRequest) {
        User exitsUser = userService.findUserByUsername(signUpRequest.getUsername());
        if (exitsUser != null) {
            return new ResponseObject("Failed", "Username already used", null);
        } else {
            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .roles(new HashSet<>())
                    .build();
            Role userRole = roleService.findByRoleName("USER");
            Role signUpRole = roleService.findByRoleName(signUpRequest.getRole());

            user.getRoles().add(userRole);
            if (signUpRole != null) {
                user.getRoles().add(signUpRole);
            } else {
                return new ResponseObject("Failed", "role not fond", null);
            }
            return new ResponseObject("ok", "Create User Successfully", userService.saveUser(user));
        }
    }

    @Override
    public ResponseObject findUserById(Long idUser) {
        User user = userService.findUserById(idUser);
        return user != null ? new ResponseObject("ok", "Find user successfully", user) :
                new ResponseObject("Failed", "Id invalid", "");
    }

    @Override
    public ResponseObject changePassword(SignUpRequest signUpRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            if (signUpRequest.getPassword().length() < 6) {
                return new ResponseObject("failed", "Password invalid", null);
            }
            existUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            return new ResponseObject("ok", "Change password successfully", userService.saveUser(existUser));
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject changeInfor(ChangeInfoRequest changeInfoRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existUser = userService.findUserByUsername(username);
        if (existUser == null) {
            return new ResponseObject("failed", "User info invalid", null);
        }
        if (changeInfoRequest.getLastName().isEmpty() || changeInfoRequest.getFirstName().isEmpty()
                || changeInfoRequest.getCccd().length() != 12 || changeInfoRequest.getNumberPhone().isEmpty()) {
            return new ResponseObject("failed", "LastName or FirstName or SDT or CCCD invalid", null);
        }
        //Set info
        existUser.setFirstName(changeInfoRequest.getFirstName());
        existUser.setLastName(changeInfoRequest.getLastName());
        existUser.setCccd(changeInfoRequest.getCccd());
        existUser.setNumberPhone(changeInfoRequest.getNumberPhone());
        existUser.setGender(changeInfoRequest.isGender());
        //set DateOfBirth
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Không cho phép chuyển đổi mềm dẻo

            Date date = dateFormat.parse(changeInfoRequest.getDateOfBirth());

            // Ngày hợp lệ, thực hiện xử lý tiếp theo
            existUser.setDateOfBirth(date);
        } catch (ParseException e) {
            // Ngày không hợp lệ, trả về thông báo lỗi
            return new ResponseObject("failed", e.getMessage(), null);
        }
        return new ResponseObject("ok", "Change info successfully", userService.saveUser(existUser));
    }

    @Override
    public ResponseObject changeAvt(ImageRequest imageRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            System.out.println("~~~>FILE: " + imageRequest.getFile().getOriginalFilename());
            String fileName = iStorageService.storeFile(imageRequest.getFile());
            if (fileName == null) {
                return new ResponseObject("failed", "file format of some file invalid", null);
            }

            Path[] paths = iStorageService.loadFile(fileName).toArray(Path[]::new);
            //Check if filePath is existing
            if (paths.length > 0) {
                Path filePath = paths[0];
                String urlPath = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "readDetailFile", filePath.getFileName().toString()).build().toUri().toString();
                System.out.println("~~~>fileURL: " + urlPath);
                if (Objects.equals(urlPath, "")) {
                    return new ResponseObject("failed", "Failed to generate file path", null);
                }
                existUser.setAvt(urlPath);
            }
            return new ResponseObject("ok", "Change avt successfully", userService.saveUser(existUser));
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject likeBaiViet(Long idBaiViet) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            BaiViet existBaiViet = baiVietRepository.findById(idBaiViet).orElse(null);
            if (existBaiViet == null) {
                return new ResponseObject("failed", "Bai viet invalid", null);
            }
            int countLikesBefore= userRepository.countLikesByIdBaiViet(existBaiViet.getIdBaiViet());
            System.out.println("~~~>Like count before " + countLikesBefore);
            existUser.getLikedBaiVietSet().add(existBaiViet);
            userService.saveUser(existUser);
            int countLikes= userRepository.countLikesByIdBaiViet(existBaiViet.getIdBaiViet());
            System.out.println("~~~>Like count after " + countLikes);
            return new ResponseObject("ok", "Like post successfully", countLikes);
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject unLikeBaiViet(Long idBaiViet) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            BaiViet existBaiViet = baiVietRepository.findById(idBaiViet).orElse(null);
            if (existBaiViet == null) {
                return new ResponseObject("failed", "Bai viet invalid", null);
            }
            existUser.getLikedBaiVietSet().removeIf(baiViet -> baiViet.getIdBaiViet().equals(existBaiViet.getIdBaiViet()));
            userService.saveUser(existUser);
            int countLikes= userRepository.countLikesByIdBaiViet(existBaiViet.getIdBaiViet());
            System.out.println("~~~>Like count before " + countLikes);
            return new ResponseObject("ok", "Unlike post successfully", countLikes);
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject findLikeBaiVietByUsername() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }

            Set<BaiViet> baiVietList = userRepository.findLikedBaiVietSetById(existUser.getId());
            return new ResponseObject("ok", "Get all liked bai viet", baiVietList);
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    /**
     * For refresh jwt token in the future
     */
    @Override
    public ResponseObject refreshToken(String token) {
        try{
            return null;
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getUserInfo(String username) {
        try{
            User existUser= userService.findUserByUsername(username);
            if(existUser==null){
                return new ResponseObject("failed", "username invalid", null);
            }
            List<NhaTro> nhaTroList= nhaTroRepository.findByUser_Id(existUser.getId());
            System.out.println("~~~>NhatroList " + nhaTroList);
            List<NhaTroDTO> nhaTroDTOList = new ArrayList<>();
            for(NhaTro nt : nhaTroList){
                List<Phong> phongList= phongRepository.findByPhongID_IdNhaTro(nt.getIdNhaTro());
                System.out.println("~~~>PhongList "+ phongList);
                NhaTroDTO nhaTroDTO= NhaTroDTO.builder()
                        .id(nt.getUser().getId())
                        .idNhaTro(nt.getIdNhaTro())
                        .tenNhaTro(nt.getTenNhaTro())
                        .tenDuong(nt.getTuyenDuong().getTenDuong())
                        .tenXa(nt.getXa().getXaID().getTenXa())
                        .tenHuyen(nt.getXa().getXaID().getTenHuyen())
                        .tenTinh(nt.getXa().getXaID().getTenTinh())
                        .lauSet(nt.getLauSet())
                        .build();
                nhaTroDTOList.add(nhaTroDTO);
            }
            List<BaiViet> baiVietList= baiVietRepository.findAllBy_User_Id(existUser.getId());
            List<BaiVietDTO> baiVietDTOList = new ArrayList<>();
            for (BaiViet bv : baiVietList) {
                BaiVietDTO baiVietDTO = baiVietServiceImpl.convertToBaiVietDTO(bv,null);
                baiVietDTOList.add(baiVietDTO);
            }
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .user(existUser)
                    .boarding_houses(nhaTroDTOList)
                    .posts(baiVietDTOList)
                    .build();
            return new ResponseObject("ok", "get all user information", userInfoDTO);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

}
