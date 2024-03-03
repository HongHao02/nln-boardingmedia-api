package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.TuyenDuong;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class XaDTO {
    private String tenXa;
    private Set<String> tuyenDuongSet;
}
