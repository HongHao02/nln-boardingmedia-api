package com.b2012202.mxhtknt.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBaiVietRequest {
    private Long idBaiViet;
    private String description;
    private boolean lock = false;
    private List<MultipartFile> files;
}
