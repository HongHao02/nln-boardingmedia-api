package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Request.JwtAuthenticationResponse;
import com.b2012202.mxhtknt.Request.LoginRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.SignUpRequest;
import com.b2012202.mxhtknt.Models.Role;
import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Services.AuthenticationService;
import com.b2012202.mxhtknt.Services.JWTService;
import com.b2012202.mxhtknt.Services.RoleService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public ResponseObject login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword())
            );
            User user = userService.findUserByUsername(loginRequest.getUsername());
            if (user != null) {
                var jwt = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
                return new ResponseObject("ok", "Login successfully",
                        JwtAuthenticationResponse.builder()
                                .token(jwt)
                                .refreshToken(refreshToken)
                                .user(user)
                                .build());
            } else {
                return new ResponseObject("Failed", "Username or password invalid", "");
            }
        } catch (Exception e) {
            return new ResponseObject("Failed", "Username or password invalid", "");
        }
    }

    @Override
    public ResponseObject signUp(SignUpRequest signUpRequest) {
        User exitsUser = userService.findUserByUsername(signUpRequest.getUsername());
        if (exitsUser != null) {
            return new ResponseObject("Failed", "Username already used", "");
        } else {
            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .roles(new HashSet<>())
                    .build();
            Role userRole = roleService.findByRoleName("USER");
            Role signUpRole = roleService.findByRoleName(signUpRequest.getRole());

            user.getRoles().add(userRole);
            if (signUpRole != null) {
                user.getRoles().add(signUpRole);
            } else {
                return new ResponseObject("Failed", "role not fond", "");
            }
            return new ResponseObject("ok", "Create User Successfully", userService.saveUser(user));
        }
    }

    @Override
    public ResponseObject findUserById(Long idUser) {
        User user = userService.findUserById(idUser);
        return user != null ? new ResponseObject("ok", "Find user successfully", user) :
                new ResponseObject("Failed", "Id invalid", "");
    }
}
