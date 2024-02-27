package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Request.NhaTroRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.EmbeddedId.XaID;
import com.b2012202.mxhtknt.Models.NhaTro;
import com.b2012202.mxhtknt.Models.TuyenDuong;
import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Models.Xa;
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

@Service
@RequiredArgsConstructor
public class NhaTroServiceImpl implements NhaTroService {
    private final XaRepository xaRepository;
    private final TuyenDuongRepository tuyenDuongRepository;
    private final UserService userService;
    private final NhaTroRepository nhaTroRepository;

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
}
