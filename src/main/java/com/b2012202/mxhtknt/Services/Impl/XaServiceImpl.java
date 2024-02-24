package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.XaRequest;
import com.b2012202.mxhtknt.Models.EmbeddedId.HuyenID;
import com.b2012202.mxhtknt.Models.EmbeddedId.XaID;
import com.b2012202.mxhtknt.Models.Huyen;
import com.b2012202.mxhtknt.Models.Xa;
import com.b2012202.mxhtknt.Repositories.HuyenRepository;
import com.b2012202.mxhtknt.Repositories.XaRepository;
import com.b2012202.mxhtknt.Services.XaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class XaServiceImpl implements XaService {
    private final XaRepository xaRepository;
    private final HuyenRepository huyenRepository;
    @Override
    public List<Xa> findAllXa() {
        try {
            return xaRepository.findAll();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public ResponseObject createXa(XaRequest xaRequest) {
        XaID xaID= XaID.builder()
                .tenXa(xaRequest.getTenXa())
                .tenHuyen(xaRequest.getTenHuyen())
                .tenTinh(xaRequest.getTenTinh())
                .build();
        HuyenID huyenID= HuyenID.builder()
                .tenTinh(xaRequest.getTenTinh())
                .tenHuyen(xaRequest.getTenHuyen())
                .build();

        Xa exitstXa = xaRepository.findById(xaID).orElse(null);

        if(exitstXa!=null){
            return new ResponseObject("failed","Xa already exits","");
        }

        Xa xa= Xa.builder()
                .xaID(new XaID())
                .huyen(new Huyen())
                .build();

        Huyen huyen= huyenRepository.findById(huyenID).orElse(null);

        if(huyen == null){
            return new ResponseObject("failed","Huyen invalid","");
        }

        xa.getXaID().setTenXa(xaRequest.getTenXa());
        xa.setHuyen(huyen);

        System.out.println("~~~>Xa "+ xa.toString());

        return new ResponseObject("ok","Create Xa successfully",xaRepository.save(xa));
    }
}
