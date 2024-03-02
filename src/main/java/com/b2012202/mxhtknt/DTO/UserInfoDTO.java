package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoDTO {
    private User user;
    private List<NhaTroDTO> boarding_houses;
    private List<BaiVietDTO> posts;
}
