package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Request.TuVanRequest;

public interface TuVanService {
    ResponseObject createTuVan(TuVanRequest tuVanRequest);

    ResponseObject getAllTuVanByNhaTroId(Long idNhaTro);

    ResponseObject getAllTuVanByIdChuTro(Long idChuTro);

    ResponseObject updateViewed(Long idTV);
}
