package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.LoaiPhong;
import com.b2012202.mxhtknt.Repositories.LoaiPhongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chutro/loaiphong")
@RequiredArgsConstructor
public class LoaiPhongController {
    private final LoaiPhongRepository loaiPhongRepository;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createLoaiPhong(@RequestBody LoaiPhong loaiPhong){
        LoaiPhong exitsLoaiPhong= loaiPhongRepository.findByTenLoai(loaiPhong.getTenLoai()).orElse(null);
        if(exitsLoaiPhong!=null){
            return ResponseEntity.ok(new ResponseObject("failed","Loai phong already exits",null));
        }
        return ResponseEntity.ok(new ResponseObject("ok","Create loai phong successfully",loaiPhongRepository.save(loaiPhong)));
    }

    @GetMapping("getAll")
    public ResponseEntity<ResponseObject> getAllLoaiPhong(){
        return ResponseEntity.ok(new ResponseObject("ok", "Get all loai phong successfully",loaiPhongRepository.findAll()));
    }
}
