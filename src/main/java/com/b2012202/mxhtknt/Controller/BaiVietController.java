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
    @GetMapping("/{idBaiViet}")
    public ResponseEntity<ResponseObject> getByIdBaiViet(@PathVariable Long idBaiViet){
        return ResponseEntity.ok(baiVietService.getByIdBaiViet(idBaiViet));
    }
//    @GetMapping("/{fileName:.+}")
//    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
//        try {
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(baiVietService.getFileByte(fileName));//dung thay cho HttpStatus.OK
//        } catch (Exception e) {
//            return ResponseEntity.noContent().build();
//        }
//    }



}
