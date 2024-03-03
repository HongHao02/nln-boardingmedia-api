package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.XaRequest;
import com.b2012202.mxhtknt.Models.Xa;
import com.b2012202.mxhtknt.Services.XaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/xa")
@RequiredArgsConstructor
public class XaController {
    private final XaService xaService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createXa(@ModelAttribute XaRequest xaRequest) {
        return ResponseEntity.ok(xaService.createXa(xaRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Xa>> getAllXa() {
        return ResponseEntity.ok(xaService.findAllXa());
    }

    @DeleteMapping("/delete/{tenTinh}/{tenHuyen}/{tenXa}")
    public ResponseEntity<ResponseObject> deleteXa(@PathVariable String tenTinh, @PathVariable String tenHuyen, @PathVariable String tenXa) {
        return ResponseEntity.ok(xaService.deleteXa(tenTinh, tenHuyen, tenXa));
    }

    @GetMapping("/sayHello")
    public ResponseEntity<ResponseObject> testSay() {
        try {
            return ResponseEntity.ok(new ResponseObject("ok", "Hello! This is Xa Hello", ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("failed", "Forbidden", e.getMessage()));
        }
    }
}
