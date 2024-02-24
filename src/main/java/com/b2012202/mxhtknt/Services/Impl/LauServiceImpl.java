package com.b2012202.mxhtknt.Services.Impl;

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

import java.util.List;

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

//            Lau exitsLau= lauRepository.findBySttLau(lauRequest.getSttLau()).orElse(null);
            Lau exitsLau= lauRepository.findBySTTLauAndIdNhaTro(lauRequest.getSttLau(), lauRequest.getIdNhaTro()).orElse(null);

            if(nhaTroExits==null || exitsLau!=null){
                return new ResponseObject("failed","Nha tro doesn't exits or sttLau already use","");
            }
            Lau lau= Lau.builder()
                    .lauID(new LauID())
                    .nhaTro(nhaTroExits)
                    .sttLau(lauRequest.getSttLau())
                    .build();

            lau.getLauID().setIdLau(getNextSequenceValue());
            return new ResponseObject("ok","Create lau for nha tro successfully",lauRepository.save(lau));
        }catch (Exception ex){
            return new ResponseObject("failed","Error when create lau for nha tro",ex.getMessage());
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
}
