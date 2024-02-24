package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.HuyenRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;

public interface HuyenService {
    ResponseObject createHuyen(HuyenRequest huyenRequest);

    ResponseObject getAllHuyen();
}
