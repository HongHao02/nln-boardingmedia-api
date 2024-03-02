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
    @DeleteMapping("delete/{tenTinh}")
    public ResponseEntity<ResponseObject> deleteTinh(@PathVariable String tenTinh){
        try{
            Tinh existTinh= tinhRepository.findById(tenTinh).orElse(null);
            if(existTinh==null){
                return ResponseEntity.badRequest().body(new ResponseObject("failed", "province invalid", null));
            }
            tinhRepository.delete(existTinh);
            return ResponseEntity.ok().body(new ResponseObject("failed","delete province successfully" , null));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ResponseObject("failed", ex.getMessage(), null));
        }
    }
}
