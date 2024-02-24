package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.LauRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.Lau;
import com.b2012202.mxhtknt.Services.LauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chutro/lau")
@RequiredArgsConstructor
public class LauController {
    private final LauService lauService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createLau(@ModelAttribute LauRequest lauRequest){
        return ResponseEntity.ok(lauService.createLau(lauRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Lau>> getAllLau(){
        return ResponseEntity.ok(lauService.getAllLau());
    }
}
