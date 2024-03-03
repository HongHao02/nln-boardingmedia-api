package com.b2012202.mxhtknt.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class HuyenDTO {
    private String tenHuyen;
    private Set<XaDTO> xaSet;
}
