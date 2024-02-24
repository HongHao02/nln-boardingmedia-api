package com.b2012202.mxhtknt.Request;

import lombok.Data;

@Data
public class PhongRequest {
    private Long idNhaTro;
    private Long idLau;
    private Long idLoai;
    private Integer sttPhong;
    private Double giaPhong;
    private boolean tinhTrang;
}
