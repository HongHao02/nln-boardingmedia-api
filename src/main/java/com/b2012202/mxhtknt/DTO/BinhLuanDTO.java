package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BinhLuanDTO {
    private Long idBL;
    private Long idBaiViet;
    private User user;
    private String noiDung;
    private LocalDateTime thoiGianBL;
    private Integer countComments;
}
