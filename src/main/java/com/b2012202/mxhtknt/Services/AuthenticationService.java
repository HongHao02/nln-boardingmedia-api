package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.DTO.LoginRequest;
import com.b2012202.mxhtknt.DTO.ResponseObject;
import com.b2012202.mxhtknt.DTO.SignUpRequest;

public interface AuthenticationService {
    ResponseObject login(LoginRequest loginRequest);

    ResponseObject signUp(SignUpRequest signUpRequest);

    ResponseObject findUserById(Long idUser);
}
