package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.HuyenRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.HuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/huyen")
@RequiredArgsConstructor
public class HuyenController {

    private final HuyenService huyenService;
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createHuyen(@ModelAttribute HuyenRequest huyenRequest){
        return ResponseEntity.ok(huyenService.createHuyen(huyenRequest));
    }

    @GetMapping("/getAllHuyen")
    public ResponseEntity<ResponseObject> getAllHuyen(){
        return ResponseEntity.ok(huyenService.getAllHuyen());
    }
    @GetMapping("/sayHello")
    public ResponseEntity<ResponseObject> testSay(){
        try{
            return ResponseEntity.ok(new ResponseObject("ok", "Hello! This is Huyen Hello", ""));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseObject("failed","Forbidden",e.getMessage()));
        }
    }
}
