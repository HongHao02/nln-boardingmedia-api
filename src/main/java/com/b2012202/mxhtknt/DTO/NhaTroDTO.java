package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.Lau;
import com.b2012202.mxhtknt.Models.NhaTro;
import com.b2012202.mxhtknt.Models.Phong;
import com.b2012202.mxhtknt.Models.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class NhaTroDTO {
    private Long id;
    private String username;
    private long idNhaTro;
    private String tenNhaTro;
    private String tenDuong;
    private String tenXa;
    private String tenHuyen;
    private String tenTinh;
    private Set<Lau> lauSet;
}
