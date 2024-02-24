package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.BinhLuanRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;

public interface BinhLuanService {
    ResponseObject createBinhLuan(BinhLuanRequest binhLuanRequest);

    ResponseObject getAllBinhLuan(Long idBaiViet);

    ResponseObject getBinhLuanById(Long idBinhLuan);
}
