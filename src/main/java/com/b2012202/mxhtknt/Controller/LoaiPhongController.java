package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.LoaiPhongRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.LoaiPhong;
import com.b2012202.mxhtknt.Repositories.LoaiPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/chutro/loaiphong")
@RequiredArgsConstructor
public class LoaiPhongController {
    private final LoaiPhongRepository loaiPhongRepository;

    @PostMapping("/createLP")
    public ResponseEntity<ResponseObject> createLP(@ModelAttribute LoaiPhongRequest loaiPhongRequest){
        try{
            LoaiPhong existLP = loaiPhongRepository.findByTenLoai(loaiPhongRequest.getTenLoai()).orElse(null);
            if(existLP != null){
                return ResponseEntity.ok(new ResponseObject("failed","tenLoai already in use", null));
            }
            LoaiPhong loaiPhong= LoaiPhong.builder()
                    .tenLoai(loaiPhongRequest.getTenLoai())
                    .phongSet(new HashSet<>())
                    .build();
            return ResponseEntity.ok(new ResponseObject("ok","create loaiPhong successfully", loaiPhongRepository.save(loaiPhong)));
        }catch (Exception ex){
            return ResponseEntity.ok(new ResponseObject("failed", ex.getMessage(), null));
        }
    }
    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("getAll")
    public ResponseEntity<ResponseObject> getAllLoaiPhong(){
        return ResponseEntity.ok(new ResponseObject("ok", "Get all loai phong successfully",loaiPhongRepository.findAll()));
    }
}
