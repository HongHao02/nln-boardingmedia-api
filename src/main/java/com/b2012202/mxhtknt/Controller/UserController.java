package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Request.ChangeInfoRequest;
import com.b2012202.mxhtknt.Request.ImageRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.SignUpRequest;
import com.b2012202.mxhtknt.Services.AuthenticationService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    @GetMapping("/sayHello")
    public ResponseEntity<ResponseObject> sayUser(){
        try{
            return ResponseEntity.ok(new ResponseObject("ok", "Hello! This is User", ""));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseObject("failed","Forbidden",e.getMessage()));
        }
    }

    //Change user information
    @PutMapping("/password")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.changePassword(signUpRequest));
    }
    @PutMapping("/info")
    public ResponseEntity<ResponseObject> changeInfo(@ModelAttribute ChangeInfoRequest changeInfoRequest){
        return ResponseEntity.ok(authenticationService.changeInfor(changeInfoRequest));
    }
    @PutMapping("/avt")
    public ResponseEntity<ResponseObject> changeAvt(@ModelAttribute ImageRequest imageRequest){
        return ResponseEntity.ok(authenticationService.changeAvt(imageRequest));
    }

    //like post
    @PutMapping("/{idBaiViet}/like")
    public ResponseEntity<ResponseObject> likeBaiViet(@PathVariable Long idBaiViet){
        return ResponseEntity.ok(authenticationService.likeBaiViet(idBaiViet));
    }
    @DeleteMapping("/{idBaiViet}/unlike")
    public ResponseEntity<ResponseObject> unLikeBaiViet(@PathVariable Long idBaiViet){
        return ResponseEntity.ok(authenticationService.unLikeBaiViet(idBaiViet));
    }
    @GetMapping("baiviet/like")
    public ResponseEntity<ResponseObject> findLikeBaiVietByUsername(){
        return ResponseEntity.ok(authenticationService.findLikeBaiVietByUsername());
    }






}
