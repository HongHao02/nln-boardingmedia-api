package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.BinhLuanRequest;
import com.b2012202.mxhtknt.Request.BinhLuanUpdate;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Services.BinhLuanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/binhluan")
@RequiredArgsConstructor
public class BinhLuanController {
    private final BinhLuanService binhLuanService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBinhLuan(@ModelAttribute BinhLuanRequest binhLuanRequest){
        return ResponseEntity.ok(binhLuanService.createBinhLuan(binhLuanRequest));
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<ResponseObject> getAllBinhLuan(@PathVariable Long id){
        return ResponseEntity.ok(binhLuanService.getAllBinhLuan(id));
    }

    @GetMapping("/findById/{idBinhLuan}")
    public ResponseEntity<ResponseObject> getBinhLuanById(@PathVariable Long idBinhLuan){
        return ResponseEntity.ok(binhLuanService.getBinhLuanById(idBinhLuan));
    }

    @DeleteMapping("/delete/{idBinhLuan}")
    public ResponseEntity<ResponseObject> deleteBinhLuan(@PathVariable Long idBinhLuan){
        return ResponseEntity.ok(binhLuanService.deleteBinhLuan(idBinhLuan));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateBinhLuan(@ModelAttribute BinhLuanUpdate binhLuanUpdate){
        return ResponseEntity.ok(binhLuanService.updateBinhLuan(binhLuanUpdate));
    }

    @GetMapping("/hello/test")
    public ResponseEntity<ResponseObject> sayHello(){
        return ResponseEntity.ok(new ResponseObject("ok","Hello from Binh luan", null));
    }
}
