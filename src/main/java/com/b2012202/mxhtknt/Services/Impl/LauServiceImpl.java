package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Models.BaiViet;
import com.b2012202.mxhtknt.Models.Phong;
import com.b2012202.mxhtknt.Request.LauRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.EmbeddedId.LauID;
import com.b2012202.mxhtknt.Models.Lau;
import com.b2012202.mxhtknt.Models.NhaTro;
import com.b2012202.mxhtknt.Repositories.LauRepository;
import com.b2012202.mxhtknt.Repositories.NhaTroRepository;
import com.b2012202.mxhtknt.Services.LauService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class LauServiceImpl implements LauService {
    private final NhaTroRepository nhaTroRepository;
    private final LauRepository lauRepository;
//    private final LauTransactionalImpl lauTransactional;
    @Override
    public ResponseObject createLau(LauRequest lauRequest) {
        try{
            NhaTro nhaTroExits = nhaTroRepository.findById(lauRequest.getIdNhaTro()).orElse(null);
            Lau exitsLau= lauRepository.findBySTTLauAndIdNhaTro(lauRequest.getSttLau(), lauRequest.getIdNhaTro()).orElse(null);

            if(nhaTroExits==null || exitsLau!=null){
                return new ResponseObject("failed","Nha tro doesn't exits or sttLau already use",null);
            }
            Lau lau= Lau.builder()
                    .lauID(new LauID())
                    .nhaTro(nhaTroExits)
                    .deleted(false)
                    .sttLau(lauRequest.getSttLau())
                    .phongSet(new TreeSet<>(Comparator.comparingInt(Phong::getSttPhong)))
                    .build();

            lau.getLauID().setIdLau(getNextSequenceValue());
            return new ResponseObject("ok","Create lau for nha tro successfully",lauRepository.save(lau));
        }catch (Exception ex){
            return new ResponseObject("failed",ex.getMessage(),null);
        }
    }

    @Override
    public List<Lau> getAllLau() {
        return lauRepository.findAll();
    }

    @Override
    public Long getNextSequenceValue() {
//        return lauTransactional.getNextSequenceValue();
        return lauRepository.getNextSequenceValue();
    }

    @Override
    public ResponseObject deleteLau(Long idNhaTro, Long idLau) {
        try{
            LauID lauID= LauID.builder()
                    .idNhaTro(idNhaTro)
                    .idLau(idLau)
                    .build();
            Lau existLau= lauRepository.findById(lauID).orElse(null);
            if(existLau==null){
                return new ResponseObject("failed","idNhaTro or idLau invalid",null);
            }
            existLau.setDeleted(true);
            for(Phong phong : existLau.getPhongSet()){
                phong.setDeleted(true);
                for(BaiViet bv: phong.getBaiVietSet()){
                    bv.setDeleted(true);
                }
            }
            lauRepository.save(existLau);
            return new ResponseObject("ok","delete lau successfully", idNhaTro +"_"+idLau);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }
}
