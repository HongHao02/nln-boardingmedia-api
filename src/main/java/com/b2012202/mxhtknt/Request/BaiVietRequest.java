package com.b2012202.mxhtknt.Request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BaiVietRequest {
    private Long idNhaTro;
    private Long idLau;
    private Long idPhong;
    private String description;
    private boolean lock = false;
    private List<MultipartFile> files;
}
