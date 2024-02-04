package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.DTO.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/sayHello")
    public ResponseEntity<ResponseObject> sayUser(){
        try{
            return ResponseEntity.ok(new ResponseObject("ok", "Hello! This is User", ""));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseObject("failed","Forbidden",e.getMessage()));
        }
    }
}
