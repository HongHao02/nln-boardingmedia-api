package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Controller.FileController;
import com.b2012202.mxhtknt.DTO.*;
import com.b2012202.mxhtknt.Models.*;
import com.b2012202.mxhtknt.Repositories.BinhLuanRepository;
import com.b2012202.mxhtknt.Repositories.UserRepository;
import com.b2012202.mxhtknt.Request.BaiVietRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.EmbeddedId.PhongID;
import com.b2012202.mxhtknt.Repositories.BaiVietRepository;
import com.b2012202.mxhtknt.Repositories.PhongRepository;
import com.b2012202.mxhtknt.Request.UpdateBaiVietRequest;
import com.b2012202.mxhtknt.Services.BaiVietService;
import com.b2012202.mxhtknt.Services.IStorageService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BaiVietServiceImpl implements BaiVietService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PhongRepository phongRepository;
    private final IStorageService iStorageService;
    private final BaiVietRepository baiVietRepository;
    private final BinhLuanRepository binhLuanRepository;

    @Override
    public ResponseObject createBaiViet(BaiVietRequest baiVietRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            PhongID phongID = PhongID.builder()
                    .idNhaTro(baiVietRequest.getIdNhaTro())
                    .idLau(baiVietRequest.getIdLau())
                    .idPhong(baiVietRequest.getIdPhong())
                    .build();
            Phong existPhong = phongRepository.findById(phongID).orElse(null);
            if (existPhong == null) {
                return new ResponseObject("failed", "post must have one Phong", null);
            }
            BaiViet baiViet = BaiViet.builder()
                    .description(baiVietRequest.getDescription())
                    .lock(baiVietRequest.isLock())
                    .user(existUser)
                    .published_at(LocalDateTime.now())
                    .phongSet(new HashSet<>())
                    .build();
            System.out.println("~~~PHONG SET: " + baiViet.getPhongSet());
            //Handle file
            List<MultipartFile> files = baiVietRequest.getFiles();
            if (!files.isEmpty()) {
                try {
                    List<String> filesURL = new ArrayList<>();
                    for (MultipartFile file : files) {
                        System.out.println("~~~>FILE: " + file.getOriginalFilename());
                        String fileName = iStorageService.storeFile(file);
                        if (fileName == null) {
                            return new ResponseObject("failed", "file format of some file invalid", null);
                        }

                        Path[] paths = iStorageService.loadFile(fileName).toArray(Path[]::new);
                        //Check if filePath is existing
                        if (paths.length > 0) {
                            Path filePath = paths[0];
                            String urlPath = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                    "readDetailFile", filePath.getFileName().toString()).build().toUri().toString();
//                            String baseUrl = "http://localhost:8080/api/v1/public/";
//                            String filename = filePath.getFileName().toString();
//                            String urlPath = baseUrl + "chutro/baiviet/" + filename;
                            System.out.println("~~~>fileURL: " + urlPath);
                            if (Objects.equals(urlPath, "")) {
                                return new ResponseObject("failed", "failed to generate file path", null);
                            }
                            filesURL.add(urlPath);
                        }
                    }
                    //add file to BaiViet
                    Set<File> fileSet = new HashSet<>();
                    if (!filesURL.isEmpty()) {
                        for (String fileName : filesURL) {
                            File file = File.builder()
                                    .url(fileName)
                                    .build();
                            fileSet.add(file);
                        }
                    } else {
                        return new ResponseObject("failed", "can not add file to fileSet", null);
                    }
                    baiViet.setFileSet(fileSet);
                } catch (Exception ex) {
                    return new ResponseObject("failed", ex.getCause().toString(), null);
                }
            }
            //add Phong to BaiViet
            baiViet.getPhongSet().add(existPhong);
            return new ResponseObject("ok", "create post successfully", baiVietRepository.save(baiViet));
        } catch (Exception e) {
            return new ResponseObject("failed", e.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getByIdBaiViet(Long idBaiViet) {
        try {
            BaiViet existBaiViet = baiVietRepository.findById(idBaiViet).orElse(null);
            if (existBaiViet == null) {
                return new ResponseObject("failed", "idBaiViet invalid", null);
            }
            List<BinhLuan> binhLuanList = binhLuanRepository.findByBaiViet_IdBaiViet(idBaiViet);
            List<BinhLuanDTO> binhLuanDTOList = new ArrayList<>();
            for (BinhLuan bl : binhLuanList) {
                binhLuanDTOList.add(BinhLuanDTO.builder()
                        .idBL(bl.getIdBL())
                        .idBaiViet(bl.getBaiViet().getIdBaiViet())
                        .user(bl.getUser())
                        .noiDung(bl.getNoiDung())
                        .thoiGianBL(bl.getThoiGianBL())
                        .build());
            }
            FullInfoBaiVietDTO fullInfoBaiVietDTO = FullInfoBaiVietDTO.builder()
                    .baiViet(convertToBaiVietDTO(existBaiViet))
                    .comments(binhLuanDTOList)
                    .build();
            return new ResponseObject("ok", "get post by ID successfully", fullInfoBaiVietDTO);
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public byte[] getFileByte(String fileName) {
        return iStorageService.readFileContent(fileName);
    }

    @Override
    public ResponseObject getBaiVietListFollowPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<BaiViet> baiVietPage = baiVietRepository.findAll(pageable);
            List<BaiViet> baiVietList = baiVietPage.getContent();
            List<BaiVietDTO> baiVietDTOList = new ArrayList<>();
            for (BaiViet bv : baiVietList) {
                int countLikes = userRepository.countLikesByIdBaiViet(bv.getIdBaiViet());
                int countComments = binhLuanRepository.countCommentsByIdBaiViet(bv.getIdBaiViet());
                BaiVietDTO baiVietDTO = convertToBaiVietDTO(bv);
                baiVietDTOList.add(baiVietDTO);
            }
            return new ResponseObject("ok", "get list of bai viet follow by page and size successfully", baiVietDTOList);
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    /**
     * Update attribute: delete, cannot find by getById(idBaiViet) similar deleted
     */
    @Override
    public ResponseObject deleteBaiViet(Long idBaiViet) {
        try {
            BaiViet existBaiViet = baiVietRepository.findById(idBaiViet).orElse(null);
            if (existBaiViet == null) {
                return new ResponseObject("failed", "idBaiViet invalid", null);
            }
            existBaiViet.setDeleted(true);
            baiVietRepository.save(existBaiViet);
            return new ResponseObject("failed", "delete bai viet successfully", idBaiViet);
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject updateBaiViet(UpdateBaiVietRequest updateBaiVietRequest) {
        try{
            BaiViet existBaiViet = baiVietRepository.findById(updateBaiVietRequest.getIdBaiViet()).orElse(null);
            if (existBaiViet == null) {
                return new ResponseObject("failed", "idBaiViet invalid", null);
            }
            if(!updateBaiVietRequest.getDescription().isEmpty()){
                existBaiViet.setDescription(updateBaiVietRequest.getDescription());
            }
            existBaiViet.setLock(updateBaiVietRequest.isLock());
            existBaiViet.setLast_update(LocalDateTime.now());
            if(!updateBaiVietRequest.getFiles().isEmpty()){
                ConvertSetFileDTO cv= iStorageService.convertToSetFile(updateBaiVietRequest.getFiles());
                if(!cv.getMessage().equals("success")){
                    return new ResponseObject("failed", cv.getMessage(), null);
                }
                existBaiViet.setFileSet(cv.getFileSet());
            }
            return new ResponseObject("ok", "Update post successfully", baiVietRepository.save(existBaiViet));
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    public BaiVietDTO convertToBaiVietDTO(BaiViet bv) {
        int countLikes = userRepository.countLikesByIdBaiViet(bv.getIdBaiViet());
        int countComments = binhLuanRepository.countCommentsByIdBaiViet(bv.getIdBaiViet());
        return BaiVietDTO.builder()
                .idBaiViet(bv.getIdBaiViet())
                .user(UserDTO.builder()
                        .id(bv.getUser().getId())
                        .username(bv.getUser().getUsername())
                        .firstName(bv.getUser().getFirstName())
                        .lastName(bv.getUser().getLastName())
                        .numberPhone(bv.getUser().getNumberPhone())
                        .gender(bv.getUser().isGender())
                        .dateOfBirth(bv.getUser().getDateOfBirth())
                        .cccd(bv.getUser().getCccd())
                        .avt(bv.getUser().getAvt())
                        .build())
                .description(bv.getDescription())
                .published_at(bv.getPublished_at())
                .last_update(bv.getLast_update())
                .lock(bv.isLock())
                .fileSet(bv.getFileSet())
                .phongSet(bv.getPhongSet())
                .countLikes(countLikes)
                .countComments(countComments)
                .build();
    }
}
