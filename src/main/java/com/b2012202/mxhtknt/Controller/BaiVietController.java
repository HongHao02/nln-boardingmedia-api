package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.BaiVietRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.BaiVietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chutro/baiviet")
@RequiredArgsConstructor
public class BaiVietController {
    private final BaiVietService baiVietService;
    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello! Get all post";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBaiViet(@ModelAttribute BaiVietRequest baiVietRequest){
        return ResponseEntity.ok(baiVietService.createBaiViet(baiVietRequest));
    }
    @DeleteMapping("/delete/{idBaiViet}")
    public ResponseEntity<ResponseObject> deleteBaiViet(@PathVariable Long idBaiViet){
        return ResponseEntity.ok(baiVietService.deleteBaiViet(idBaiViet));
    }
    @GetMapping("/{idBaiViet}")
    public ResponseEntity<ResponseObject> getByIdBaiViet(@PathVariable Long idBaiViet){
        return ResponseEntity.ok(baiVietService.getByIdBaiViet(idBaiViet));
    }





}
