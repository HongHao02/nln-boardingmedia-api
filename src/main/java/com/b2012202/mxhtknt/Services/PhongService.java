package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.PhongRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;

public interface PhongService {
    ResponseObject createPhong(PhongRequest phongRequest);

    ResponseObject getPhongByID(Long idPhong);

    ResponseObject updateTinhTrang(Long idPhong);
}
