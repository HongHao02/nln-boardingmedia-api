package com.b2012202.mxhtknt.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullInfoBaiVietDTO {
    private BaiVietDTO baiViet;
    private List<BinhLuanDTO> comments;
}
