package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Request.PhongRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.EmbeddedId.LauID;
import com.b2012202.mxhtknt.Models.EmbeddedId.PhongID;
import com.b2012202.mxhtknt.Models.Lau;
import com.b2012202.mxhtknt.Models.LoaiPhong;
import com.b2012202.mxhtknt.Models.Phong;
import com.b2012202.mxhtknt.Repositories.LauRepository;
import com.b2012202.mxhtknt.Repositories.LoaiPhongRepository;
import com.b2012202.mxhtknt.Repositories.PhongRepository;
import com.b2012202.mxhtknt.Services.PhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhongServiceImpl implements PhongService {
    private final PhongRepository phongRepository;
    private final LoaiPhongRepository loaiPhongRepository;
    private final LauRepository lauRepository;

    @Override
    public ResponseObject createPhong(PhongRequest phongRequest) {
        Phong phongExits= phongRepository.findByIDNhaTroAndIdLauAndSTTPhong(phongRequest.getIdNhaTro(),phongRequest.getIdLau(),phongRequest.getSttPhong())
                .orElse(null);
        if(phongExits!=null){
            return new ResponseObject("failed","Phong already exits", null);
        }
        LoaiPhong loaiPhongExists= loaiPhongRepository.findById(phongRequest.getIdLoai()).orElse(null);
        if(loaiPhongExists==null){
            return new ResponseObject("failed","Loai Phong invalid", null);
        }
        LauID lauID= LauID.builder()
                .idLau(phongRequest.getIdLau())
                .idNhaTro(phongRequest.getIdNhaTro())
                .build();
        Lau lauExists= lauRepository.findById(lauID).orElse(null);
        if(lauExists==null){
            return new ResponseObject("failed","Lau invalid", null);
        }

        Phong phong= Phong.builder()
                .phongID(new PhongID())
                .lau(lauExists)
                .loaiPhong(loaiPhongExists)
                .sttPhong(phongRequest.getSttPhong())
                .giaPhong(phongRequest.getGiaPhong())
                .tinhTrang(phongRequest.isTinhTrang())
                .build();
        phong.getPhongID().setIdPhong(phongRepository.getNextSequenceValue());
        phong.getPhongID().setIdLau(lauID.getIdLau());
        phong.getPhongID().setIdNhaTro(lauID.getIdNhaTro());
        return new ResponseObject("ok","Create Phong successfully", phongRepository.save(phong));
    }

    @Override
    public ResponseObject getPhongByID(Long idPhong) {
        return new ResponseObject("ok","Find phong successfully", phongRepository.findByIDPhong(idPhong).orElse(null));
    }

    @Override
    public ResponseObject updateTinhTrang(Long idPhong) {
        try{
            Phong existPhong= phongRepository.findByPhongID_IdPhong(idPhong).orElse(null);
            if(existPhong==null){
                return new ResponseObject("failed", "room id invalid", null);
            }
            existPhong.setTinhTrang(!existPhong.isTinhTrang());
            return new ResponseObject("ok","update status of room successfully", phongRepository.save(existPhong));
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }
}
