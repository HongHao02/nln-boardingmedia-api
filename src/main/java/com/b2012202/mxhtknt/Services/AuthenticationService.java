package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.LoginRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.SignUpRequest;

public interface AuthenticationService {
    ResponseObject login(LoginRequest loginRequest);

    ResponseObject signUp(SignUpRequest signUpRequest);

    ResponseObject findUserById(Long idUser);
}
