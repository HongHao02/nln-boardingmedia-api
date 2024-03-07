package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.DTO.ListTuVanDTO;
import com.b2012202.mxhtknt.DTO.TuVanDTO;
import com.b2012202.mxhtknt.Models.*;
import com.b2012202.mxhtknt.Models.EmbeddedId.ChiTietTuVanID;
import com.b2012202.mxhtknt.Models.EmbeddedId.PhongID;
import com.b2012202.mxhtknt.Repositories.BaiVietRepository;
import com.b2012202.mxhtknt.Repositories.ChiTietTuVanRepository;
import com.b2012202.mxhtknt.Repositories.PhongRepository;
import com.b2012202.mxhtknt.Repositories.TuVanRepository;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.TuVanRequest;
import com.b2012202.mxhtknt.Services.TuVanService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TuVanServiceImpl implements TuVanService {
    private final UserService userService;

    private final PhongRepository phongRepository;
    private final BaiVietRepository baiVietRepository;
    private final TuVanRepository tuVanRepository;
    private final ChiTietTuVanRepository chiTietTuVanRepository;

    @Override
    public ResponseObject createTuVan(TuVanRequest tuVanRequest) {
        try {
            //Get user
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            //Check TuVan if it already exists
            TuVan existTuVan = tuVanRepository.findByPhongAndId(username, tuVanRequest.getIdBaiViet())
                    .orElse(null);
            if (existTuVan != null) {
                return new ResponseObject("failed", "Tu van already exists", null);
            }
            //Get Phong
            PhongID phongID = PhongID.builder()
                    .idNhaTro(tuVanRequest.getIdNhaTro())
                    .idLau(tuVanRequest.getIdLau())
                    .idPhong(tuVanRequest.getIdPhong())
                    .build();
            Phong existPhong = phongRepository.findById(phongID).orElse(null);
            if (existPhong == null) {
                return new ResponseObject("failed", "Phong invalid", null);
            }
            //Get BaiViet
            BaiViet existBaiViet = baiVietRepository.findById(tuVanRequest.getIdBaiViet()).orElse(null);
            if (existBaiViet == null) {
                return new ResponseObject("failed", "BaiViet invalid", null);
            }
            TuVan tuVan = TuVan.builder()
                    .baiViet(existBaiViet)
                    .user(existUser)
                    .viewed(false)
                    .chiTietTuVanSet(new HashSet<>())
                    .build();
            TuVan tuVanSaved = tuVanRepository.save(tuVan);
            try {
                ChiTietTuVan chiTietTuVan = ChiTietTuVan.builder()
                        .chiTietTuVanID(new ChiTietTuVanID())
                        .tuVan(tuVanSaved)
                        .phong(existPhong)
                        .thoiGianTuVan(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                        .build();
                tuVanSaved.getChiTietTuVanSet().add(chiTietTuVan);
                return new ResponseObject("ok", "Create tu van successfully", tuVanRepository.save(tuVanSaved));
            } catch (Exception ex) {
                return new ResponseObject("SQL failed", ex.getMessage(), null);
            }
        } catch (Exception ex) {
            return new ResponseObject("Server failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getAllTuVanByNhaTroId(Long idNhaTro) {
        return null;
    }

    @Override
    public ResponseObject getAllTuVanByIdChuTro(Long idChuTro) {
        try {
            User existUser = userService.findUserById(idChuTro);
            if (existUser == null) {
                return new ResponseObject("failed", "User invalid", null);
            }
            Integer countTuVans = tuVanRepository.countTuVanById(existUser.getId());
            List<TuVan> tuVanList = tuVanRepository.findByBaiViet_user_id(idChuTro);
            if (tuVanList == null) {
                return new ResponseObject("ok", "There is any requests", null);
            }
            List<TuVanDTO> tuVanDTOList = new ArrayList<>();
            for (TuVan tv : tuVanList) {
                TuVanDTO tuVanDTO = TuVanDTO.builder()
                        .idTV(tv.getIdTV())
                        .idBaiViet(tv.getBaiViet().getIdBaiViet())
                        .user(tv.getUser())
                        .chiTietTuVanSet(tv.getChiTietTuVanSet())
                        .viewed(tv.isViewed())
                        .build();
                tuVanDTOList.add(tuVanDTO);
            }
            return new ResponseObject("ok", "get all tu van requests for chu tro", ListTuVanDTO.builder()
                    .countTuVans(countTuVans)
                    .tuVanDTOList(tuVanDTOList)
                    .build());
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject updateViewed(Long idTV) {
        try {
            TuVan existTuVan = tuVanRepository.findById(idTV).orElse(null);
            if (existTuVan == null) {
                return new ResponseObject("failed", "Tu van invalid", null);
            }
            existTuVan.setViewed(true);

            return new ResponseObject("ok", "Update viewed of tu van successfully", tuVanRepository.save(existTuVan));
        } catch (Exception ex) {
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getAllTuVanByIdUser(Long id) {
        try{
            User existUser = userService.findUserById(id);
            if (existUser == null) {
                return new ResponseObject("failed", "User invalid", null);
            }
            Integer countViewed = tuVanRepository.countViewedByUserId(existUser.getId());
            List<TuVan> tuVanList = tuVanRepository.findByUserId(existUser.getId());
            if (tuVanList == null) {
                return new ResponseObject("ok", "There is any requests", null);
            }
            List<TuVanDTO> tuVanDTOList = new ArrayList<>();
            for (TuVan tv : tuVanList) {
                TuVanDTO tuVanDTO = TuVanDTO.builder()
                        .idTV(tv.getIdTV())
                        .idBaiViet(tv.getBaiViet().getIdBaiViet())
                        .user(null)
                        .chiTietTuVanSet(tv.getChiTietTuVanSet())
                        .viewed(tv.isViewed())
                        .build();
                tuVanDTOList.add(tuVanDTO);
            }
            return new ResponseObject("ok", "get all tu van requests for chu tro", ListTuVanDTO.builder()
                    .countViewed(countViewed)
                    .tuVanDTOList(tuVanDTOList)
                    .build());
        }catch (Exception ex){
            return new ResponseObject("failed", ex.getMessage(), null);
        }
    }
}
