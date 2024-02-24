package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.XaRequest;
import com.b2012202.mxhtknt.Models.Xa;

import java.util.List;

public interface XaService {
    List<Xa> findAllXa();

    ResponseObject createXa(XaRequest xaRequest);
}
