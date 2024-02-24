package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.*;

public interface AuthenticationService {
    ResponseObject login(LoginRequest loginRequest);

    ResponseObject signUp(SignUpRequest signUpRequest);

    ResponseObject findUserById(Long idUser);

    ResponseObject changePassword(SignUpRequest user);

    ResponseObject changeInfor(ChangeInfoRequest changeInfoRequest);

    ResponseObject changeAvt(ImageRequest imageRequest);

    ResponseObject likeBaiViet(Long idBaiViet);

    ResponseObject unLikeBaiViet(Long idBaiViet);

    ResponseObject findLikeBaiVietByUsername();
}
