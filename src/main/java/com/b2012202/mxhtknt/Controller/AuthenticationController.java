package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.DTO.LoginRequest;
import com.b2012202.mxhtknt.DTO.ResponseObject;
import com.b2012202.mxhtknt.DTO.SignUpRequest;
import com.b2012202.mxhtknt.Models.Role;
import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Services.AuthenticationService;
import com.b2012202.mxhtknt.Services.RoleService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
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
