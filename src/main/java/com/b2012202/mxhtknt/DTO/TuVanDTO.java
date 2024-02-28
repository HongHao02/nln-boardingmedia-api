package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.ChiTietTuVan;
import com.b2012202.mxhtknt.Models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TuVanDTO {
    private Long idTV;
    private Long idBaiViet;
    private User user;
    private Set<ChiTietTuVan> chiTietTuVanSet= new HashSet<>();
    private boolean viewed;
}
