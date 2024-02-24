package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Repositories.BinhLuanRepository;
import com.b2012202.mxhtknt.Services.BinhLuanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {
    private final BinhLuanRepository binhLuanRepository;
    private final BinhLuanService binhLuanService;
    //Get all binh luan of single baiViet
    @GetMapping("/binhluan/{idBaiViet}")
    public ResponseEntity<ResponseObject> getAllBinhLuan(@PathVariable Long idBaiViet){
        return ResponseEntity.ok(binhLuanService.getAllBinhLuan(idBaiViet));
//        return ResponseEntity.ok(new ResponseObject("ok","Get all comments of post", binhLuanRepository.findByBaiViet_IdBaiViet(idBaiViet)));
    }
    //For test authorization
    @GetMapping("/binhluan/hello")
    public ResponseEntity<ResponseObject> sayHello(){
        return ResponseEntity.ok(new ResponseObject("ok","Hello this is public request", null));
    }
}
