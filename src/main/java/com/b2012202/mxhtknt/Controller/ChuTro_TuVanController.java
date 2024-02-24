package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.TuVanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chutro/tuvan")
@RequiredArgsConstructor
public class ChuTro_TuVanController {
    private final TuVanService tuVanService;
    @PutMapping("/viewed/{idTV}")
    public ResponseEntity<ResponseObject> updateViewed(@PathVariable Long idTV){
        return ResponseEntity.ok(tuVanService.updateViewed(idTV));
    }
}
