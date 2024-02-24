package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BinhLuanDTO {
    private Long idBL;
    private Long idBaiViet;
    private User user;
    private String noiDung;
}
