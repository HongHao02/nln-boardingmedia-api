package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Models.NhaTro;
import com.b2012202.mxhtknt.Request.NhaTroRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import org.springframework.data.domain.Page;

public interface NhaTroService {
    ResponseObject createNhaTro(NhaTroRequest nhaTroRequest);

    ResponseObject getAllNhaTro();

    ResponseObject findByTenNhaTro(String tenNhaTro, int page, int size);

    ResponseObject findNhaTroByAbsoluteAddress(String tenDuong, String tenXa, String tenHuyen, String tenTinh, int page, int size);

    ResponseObject findNhaTroById();

    ResponseObject getNhaTroByIdNhaTro(Long idNhaTro);

    ResponseObject deleteNhaTro(Long idNhaTro);
}
