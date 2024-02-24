package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Request.LoginRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.SignUpRequest;
import com.b2012202.mxhtknt.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public  ResponseEntity<ResponseObject> logIn(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @GetMapping("/findUserById/{idUser}")
    public ResponseEntity<ResponseObject> findUserById(@PathVariable Long idUser){
        return ResponseEntity.ok(authenticationService.findUserById(idUser));
    }
}
