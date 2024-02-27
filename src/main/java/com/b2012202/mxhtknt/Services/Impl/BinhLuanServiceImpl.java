package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.DTO.BinhLuanDTO;
import com.b2012202.mxhtknt.Repositories.UserRepository;
import com.b2012202.mxhtknt.Request.BinhLuanRequest;
import com.b2012202.mxhtknt.Request.BinhLuanUpdate;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.BaiViet;
import com.b2012202.mxhtknt.Models.BinhLuan;
import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Repositories.BaiVietRepository;
import com.b2012202.mxhtknt.Repositories.BinhLuanRepository;
import com.b2012202.mxhtknt.Services.BinhLuanService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinhLuanServiceImpl implements BinhLuanService {
    private final BinhLuanRepository binhLuanRepository;
    private final BaiVietRepository baiVietRepository;
    private final UserService userService;
    @Override
    public ResponseObject createBinhLuan(BinhLuanRequest binhLuanRequest) {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            BaiViet existBaiViet= baiVietRepository.findById(binhLuanRequest.getIdBaiViet()).orElse(null);
            if(existBaiViet==null){
                return new ResponseObject("failed", "BaiViet invalid", null);
            }
            BinhLuan binhLuan= BinhLuan.builder()
                    .baiViet(existBaiViet)
                    .user(existUser)
                    .noiDung(binhLuanRequest.getNoiDung())
                    .thoiGianBL(LocalDateTime.now())
                    .build();

            return new ResponseObject("ok","Create binh luan successfully", binhLuanRepository.save(binhLuan));
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getAllBinhLuan(Long idBaiViet) {
        try{
            List<BinhLuan> binhLuanList = binhLuanRepository.findByBaiViet_IdBaiViet(idBaiViet);
            System.out.println("~~~>Binh luan list " + binhLuanList);
            List<BinhLuanDTO> binhLuanDTOList= new ArrayList<>();
            for(BinhLuan bl: binhLuanList){
                BinhLuanDTO blDTO= BinhLuanDTO.builder()
                        .idBL(bl.getIdBL())
                        .user(bl.getUser())
                        .idBaiViet(bl.getBaiViet().getIdBaiViet())
                        .noiDung(bl.getNoiDung())
                        .thoiGianBL(bl.getThoiGianBL())
                        .build();
                binhLuanDTOList.add(blDTO);
            }
            if(binhLuanDTOList.isEmpty()){
                return new ResponseObject("ok","post do not has any comments", null);
            }
            return new ResponseObject("ok","find all comments of the post", binhLuanDTOList);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getBinhLuanById(Long idBinhLuan) {
        try{
            BinhLuan existBinhLuan= binhLuanRepository.findById(idBinhLuan).orElse(null);
            if(existBinhLuan==null){
                return new ResponseObject("ok","Invalid comment", null);
            }
            return new ResponseObject("ok","Get comment by id", existBinhLuan);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject deleteBinhLuan(Long idBinhLuan) {
        try{
            BinhLuan existBinhLuan= binhLuanRepository.findById(idBinhLuan).orElse(null);
            if(existBinhLuan==null){
                return new ResponseObject("failed","Binh luan invalid", null);
            }
            binhLuanRepository.delete(existBinhLuan);
            return new ResponseObject("ok","Delete Binh luan successfully", idBinhLuan);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject updateBinhLuan(BinhLuanUpdate binhLuanRequest) {
        try{
            BinhLuan existBinhLuan= binhLuanRepository.findById(binhLuanRequest.getIdBinhLuan()).orElse(null);
            if(existBinhLuan==null){
                return new ResponseObject("failed","Binh luan invalid", null);
            }
            existBinhLuan.setNoiDung(binhLuanRequest.getNoiDung());
            return new ResponseObject("ok","Update Binh luan successfully", existBinhLuan);
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }
}
