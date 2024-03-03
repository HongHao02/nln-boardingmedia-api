package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.DTO.HuyenDTO;
import com.b2012202.mxhtknt.DTO.TinhDTO;
import com.b2012202.mxhtknt.DTO.XaDTO;
import com.b2012202.mxhtknt.Models.*;
import com.b2012202.mxhtknt.Repositories.TinhRepository;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.UpdateTinhRequest;
import com.b2012202.mxhtknt.Services.TinhService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TinhServiceImpl implements TinhService {
    private final TinhRepository tinhRepository;
    @Override
    public ResponseObject getAllAddress() {
        try{
            List<Tinh> tinhList= tinhRepository.findAll();
            List<TinhDTO> tinhDTOList= new ArrayList<>();
            for(Tinh tinh: tinhList){
                Set<HuyenDTO> huyenDTOSet= new HashSet<>();
                for(Huyen huyen: tinh.getHuyenSet()){
                    Set<XaDTO> xaDTOSet= new HashSet<>();
                    for(Xa xa: huyen.getXaSet()){
                        Set<String> tuyenDuongSet= new HashSet<>();
                        for(TuyenDuong tuyenDuong: xa.getTuyenDuongSet()){
                            tuyenDuongSet.add(tuyenDuong.getTenDuong());
                        }
                        XaDTO xaDTO= XaDTO.builder()
                                .tenXa(xa.getXaID().getTenXa())
                                .tuyenDuongSet(tuyenDuongSet)
                                .build();
                        xaDTOSet.add(xaDTO);
                    }
                    HuyenDTO huyenDTO= HuyenDTO.builder()
                            .tenHuyen(huyen.getHuyenID().getTenHuyen())
                            .xaSet(xaDTOSet)
                            .build();
                    huyenDTOSet.add(huyenDTO);
                }
                TinhDTO tinhDTO= TinhDTO.builder()
                        .tenTinh(tinh.getTenTinh())
                        .huyenSet(huyenDTOSet)
                        .build();
                tinhDTOList.add(tinhDTO);
            }
            return new ResponseObject("ok", "Get list address successfully", tinhDTOList);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

//    @Override
//    public ResponseObject updateTenTinh(UpdateTinhRequest updateTinhRequest) {
//        try{
//            if(updateTinhRequest.getTenTinh().equals(updateTinhRequest.getUpdateTenTinh())){
//                return new ResponseObject("failed","update name must be difference old name",null);
//            }
//            Tinh existTinh = tinhRepository.findById(updateTinhRequest.getTenTinh()).orElse(null);
//            if(existTinh==null){
//                return new ResponseObject("failed","province's name invalid",null);
//            }
//            if(updateTinhRequest.getUpdateTenTinh().isEmpty()){
//                return new ResponseObject("failed","province's name must be not null",null);
//            }
//            Tinh updateTinh = tinhRepository.findById(updateTinhRequest.getUpdateTenTinh()).orElse(null);
//            if(updateTinh!=null){
//                return new ResponseObject("failed","name currently in use",null);
//            }
//            existTinh.setTenTinh(updateTinhRequest.getUpdateTenTinh());
//            return new ResponseObject("ok","update province's name successfully", tinhRepository.save(existTinh));
//        }catch (Exception ex){
//            return new ResponseObject("failed", ex.getMessage(), null);
//        }
//    }
}
