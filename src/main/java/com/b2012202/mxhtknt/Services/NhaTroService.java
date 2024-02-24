package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.NhaTroRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;

public interface NhaTroService {
    ResponseObject createNhaTro(NhaTroRequest nhaTroRequest);

    ResponseObject getAllNhaTro();
}
