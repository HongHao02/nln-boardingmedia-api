package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.NhaTroRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.NhaTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chutro/nhatro")
@RequiredArgsConstructor
public class NhaTroController {
    private final NhaTroService nhaTroService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNhaTro(@ModelAttribute NhaTroRequest nhaTroRequest){
        return ResponseEntity.ok(nhaTroService.createNhaTro(nhaTroRequest));
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllNhaTro(){
        return ResponseEntity.ok(nhaTroService.getAllNhaTro());
    }


}
