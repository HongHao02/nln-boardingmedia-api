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

import java.util.HashSet;

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
                    .xaSet(new HashSet<>())
                    .tinh(new Tinh())
                    .build();

            huyen.getHuyenID().setTenHuyen(huyenID.getTenHuyen());
            huyen.setTinh(exitsTinh);
            return new ResponseObject("ok", "create district successfully", huyenRepository.save(huyen) );

        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getAllHuyen() {
        return new ResponseObject("ok", "Get all Huyen", huyenRepository.findAll());
    }

    @Override
    public ResponseObject deleteHuyen(String tenTinh, String tenHuyen) {
        try{
            HuyenID huyenID= HuyenID.builder()
                    .tenTinh(tenTinh)
                    .tenHuyen(tenHuyen)
                    .build();
            Huyen existHuyen= huyenRepository.findById(huyenID).orElse(null);
            if(existHuyen==null){
                return new ResponseObject("failed","province's name or district's name invalid", null);
            }
            huyenRepository.delete(existHuyen);
            return new ResponseObject("ok","delete district successfully", tenTinh+"_"+tenHuyen);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }
}
