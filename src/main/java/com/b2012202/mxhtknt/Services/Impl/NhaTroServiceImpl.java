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
            return new ResponseObject("ok", "Nha Tro must inquire in the same Huyen", ex.getMessage());
        }
    }

    @Override
    public ResponseObject getAllNhaTro() {
        return new ResponseObject("ok", "Get all nhatro", nhaTroRepository.findAll());
    }
}
