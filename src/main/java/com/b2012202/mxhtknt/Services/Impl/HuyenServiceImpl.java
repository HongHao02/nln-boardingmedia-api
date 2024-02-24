package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Request.HuyenRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.EmbeddedId.HuyenID;
import com.b2012202.mxhtknt.Models.Huyen;
import com.b2012202.mxhtknt.Models.Tinh;
import com.b2012202.mxhtknt.Repositories.HuyenRepository;
import com.b2012202.mxhtknt.Repositories.TinhRepository;
import com.b2012202.mxhtknt.Services.HuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HuyenServiceImpl implements HuyenService {
    private final TinhRepository tinhRepository;
    private final HuyenRepository huyenRepository;

    @Override
    public ResponseObject createHuyen(HuyenRequest huyenRequest) {
        try {
            Tinh exitsTinh = tinhRepository.findById(huyenRequest.getTenTinh()).orElse(null);
            HuyenID huyenID = HuyenID.builder()
                    .tenHuyen(huyenRequest.getTenHuyen())
                    .tenTinh(huyenRequest.getTenTinh())
                    .build();

            Huyen exitsHuyen = huyenRepository.findById(huyenID).orElse(null);
            assert exitsHuyen != null;
            System.out.println("~~~ExitsHuyen " + exitsHuyen);

            if (exitsTinh == null) {
                return new ResponseObject("failed", "Tinh's name invalid", "");
            }
            if (exitsHuyen != null) {
                return new ResponseObject("failed", "Huyen already exits", exitsHuyen);
            }
            Huyen huyen = Huyen.builder()
                    .huyenID(new HuyenID())
                    .tinh(new Tinh())
                    .build();

            huyen.getHuyenID().setTenHuyen(huyenID.getTenHuyen());
            huyen.setTinh(exitsTinh);
            System.out.println("~~~>Saved Huyen " + huyen.toString());
            Huyen saveHuyen= huyenRepository.save(huyen);
            return new ResponseObject("ok", "Create Huyen successfully", saveHuyen );

        } catch (Exception ex) {
            return new ResponseObject("failed", "Huyen_Server error", ex.getMessage());
        }
    }

    @Override
    public ResponseObject getAllHuyen() {
        return new ResponseObject("ok", "Get all Huyen", huyenRepository.findAll());
    }
}
