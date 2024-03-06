package com.b2012202.mxhtknt.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhongDTO {
    private Long idNhaTro;
    private Long idLau;
    private Long idPhong;
    private String tenNhaTro;
    private Integer sttLau;
    private Integer sttPhong;
    private Double giaPhong;
    private boolean tinhTrang;
    private String tenDuong;
    private String tenXa;
    private String tenHuyen;
    private String tenTinh;
    private Boolean deleted;
}
