package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.PhongRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.PhongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chutro/phongtro")
@RequiredArgsConstructor
public class PhongController {
    private final PhongService phongService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createPhong(@ModelAttribute PhongRequest phongRequest){
        return ResponseEntity.ok(phongService.createPhong(phongRequest));
    }

    @GetMapping("/{idPhong}")
    public ResponseEntity<ResponseObject>getPhongById(@PathVariable Long idPhong){
        return ResponseEntity.ok(phongService.getPhongByID(idPhong));
    }

}
