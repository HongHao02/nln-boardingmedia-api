package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.DTO.NhaTroDTO;
import com.b2012202.mxhtknt.Models.*;
import com.b2012202.mxhtknt.Repositories.PhongRepository;
import com.b2012202.mxhtknt.Request.NhaTroRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.EmbeddedId.XaID;
import com.b2012202.mxhtknt.Repositories.NhaTroRepository;
import com.b2012202.mxhtknt.Repositories.TuyenDuongRepository;
import com.b2012202.mxhtknt.Repositories.XaRepository;
import com.b2012202.mxhtknt.Services.NhaTroService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NhaTroServiceImpl implements NhaTroService {
    private final XaRepository xaRepository;
    private final TuyenDuongRepository tuyenDuongRepository;
    private final UserService userService;
    private final NhaTroRepository nhaTroRepository;
    private final PhongRepository phongRepository;

    @Override
    public ResponseObject createNhaTro(NhaTroRequest nhaTroRequest) {
        try {
            XaID xaID = XaID.builder()
                    .tenXa(nhaTroRequest.getTenXa())
                    .tenHuyen(nhaTroRequest.getTenHuyen())
                    .tenTinh(nhaTroRequest.getTenTinh())
                    .build();

            Xa exitsXa = xaRepository.findById(xaID).orElse(null);
            TuyenDuong exitsTuyenDuong = tuyenDuongRepository.findById(nhaTroRequest.getTenDuong()).orElse(null);
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User exitsUser = userService.findUserByUsername(username);

            if (exitsXa != null && exitsTuyenDuong != null && exitsUser != null) {
                NhaTro nhaTro = NhaTro.builder()
                        .tenNhaTro(nhaTroRequest.getTenNhaTro())
                        .tuyenDuong(exitsTuyenDuong)
                        .xa(exitsXa)
                        .user(exitsUser)
                        .build();
                return new ResponseObject("ok", "Create NhaTro successfully", nhaTroRepository.save(nhaTro));
            }
            return new ResponseObject("failed", "Create NhaTro failed", "");
        } catch (Exception ex) {
            return new ResponseObject("FAILED: Name of Nha tro must different in the same Huyen of Tinh", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getAllNhaTro() {
        return new ResponseObject("ok", "Get all nhatro", nhaTroRepository.findAll());
    }

    @Override
    public ResponseObject findByTenNhaTro(String tenNhaTro, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return new ResponseObject("ok", "Get list nha tro By ten nha tro containing", nhaTroRepository.findByTenContaining(tenNhaTro, pageable));
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject findNhaTroByAbsoluteAddress(String tenDuong, String tenXa, String tenHuyen, String tenTinh, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            if(tenDuong.isEmpty() || tenXa.isEmpty() || tenHuyen.isEmpty() || tenTinh.isEmpty()){
                return new ResponseObject("failed", "The address must be not null", null);
            }
            XaID xaID= XaID.builder()
                    .tenXa(tenXa)
                    .tenHuyen(tenHuyen)
                    .tenTinh(tenTinh)
                    .build();
            Xa existXa = xaRepository.findById(xaID).orElse(null);
            if(existXa==null){
                return new ResponseObject("failed", "Address invalid", null);
            }
            TuyenDuong existTuyenDuong= tuyenDuongRepository.findById(tenDuong).orElse(null);
            if(existTuyenDuong==null || !existXa.getTuyenDuongSet().contains(existTuyenDuong)){
                return new ResponseObject("failed", "Ten duong invalid", null);
            }
            return new ResponseObject("ok", "Get list nha tro by absolute address", nhaTroRepository.findByAbsoluteAddress(tenDuong, tenXa, tenHuyen, tenTinh, pageable));
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject findNhaTroById() {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
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
            return new ResponseObject("ok", "Get nha tro by id User successfully", nhaTroDTOList);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getNhaTroByIdNhaTro(Long idNhaTro) {
        try{
            NhaTro eixisNhaTro= nhaTroRepository.findById(idNhaTro).orElse(null);
            if(eixisNhaTro==null){
                return new ResponseObject("failed","idNhaTro invalid",null);
            }
            NhaTroDTO nhaTroDTO= NhaTroDTO.builder()
                    .id(eixisNhaTro.getUser().getId())
                    .idNhaTro(eixisNhaTro.getIdNhaTro())
                    .tenNhaTro(eixisNhaTro.getTenNhaTro())
                    .tenDuong(eixisNhaTro.getTuyenDuong().getTenDuong())
                    .tenXa(eixisNhaTro.getXa().getXaID().getTenXa())
                    .tenHuyen(eixisNhaTro.getXa().getXaID().getTenHuyen())
                    .tenTinh(eixisNhaTro.getXa().getXaID().getTenTinh())
                    .lauSet(eixisNhaTro.getLauSet())
                    .build();
            return new ResponseObject("ok", "get nha tro by idNhaTro successfully", nhaTroDTO);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }
}
