package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.LauRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.Lau;

import java.util.List;

public interface LauService {

    ResponseObject createLau(LauRequest lauRequest);

    List<Lau> getAllLau();

    Long getNextSequenceValue();

    ResponseObject deleteLau(Long idNhaTro, Long idLau);
}
