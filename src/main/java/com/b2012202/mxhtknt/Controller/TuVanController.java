package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.TuVanRequest;
import com.b2012202.mxhtknt.Services.TuVanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/khachthue/tuvan")
@RequiredArgsConstructor
public class TuVanController {
    private final TuVanService tuVanService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTuVan(@ModelAttribute TuVanRequest tuVanRequest){
        return ResponseEntity.ok(tuVanService.createTuVan(tuVanRequest));
    }
}
