package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.TuyenDuongRequest;
import com.b2012202.mxhtknt.Services.TuyenDuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/tuyenduong")
@RequiredArgsConstructor
public class TuyenDuongConTroller {
    private final TuyenDuongService tuyenDuongService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTuyenDuong(@ModelAttribute TuyenDuongRequest tuyenDuongRequest){
        return ResponseEntity.ok(tuyenDuongService.createTuyenDuong(tuyenDuongRequest));
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> createTuyenDuong(){
        return ResponseEntity.ok(tuyenDuongService.getAllTuyenDuong());
    }

    @PutMapping("/addTDToXa")
    public ResponseEntity<ResponseObject> addTuyenDuongToXa(@ModelAttribute TuyenDuongRequest tuyenDuongRequest){
        return ResponseEntity.ok(tuyenDuongService.addTuyenDuongToXa(tuyenDuongRequest));
    }


}
