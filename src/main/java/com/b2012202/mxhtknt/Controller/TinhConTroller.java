package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.Tinh;
import com.b2012202.mxhtknt.Repositories.TinhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/tinh")
@RequiredArgsConstructor
public class TinhConTroller {

    private final TinhRepository tinhRepository;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTinh(@ModelAttribute Tinh tinh) {
        System.out.println("~~~TINH REQUEST "+ tinh.getTenTinh());
        var exitsTinh = tinhRepository.findById(tinh.getTenTinh()).orElse(null);

        if (exitsTinh == null) {
            Tinh saveTinh = Tinh.builder()
                    .tenTinh(tinh.getTenTinh())
                    .build();
            return ResponseEntity.ok(new ResponseObject("ok", "Create Tinh successfully", tinhRepository.save(saveTinh)
            ));
        }
        return ResponseEntity.ok(new ResponseObject("failed", "Tinh's name already exits", ""));
    }

    @GetMapping("/getAll")
    public List<Tinh> getAll(){
        return tinhRepository.findAll();
    }
    @GetMapping("/sayHello")
    public ResponseEntity<ResponseObject> testSay(){
        try{
            return ResponseEntity.ok(new ResponseObject("ok", "Hello! This is Tinh Hello", ""));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseObject("failed","Forbidden",e.getMessage()));
        }
    }
}
