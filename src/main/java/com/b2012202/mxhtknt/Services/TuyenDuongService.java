package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.TuyenDuongRequest;

public interface TuyenDuongService {
    ResponseObject createTuyenDuong(TuyenDuongRequest tuyenDuongRequest);

    ResponseObject getAllTuyenDuong();

    ResponseObject addTuyenDuongToXa(TuyenDuongRequest tuyenDuongRequest);
}
