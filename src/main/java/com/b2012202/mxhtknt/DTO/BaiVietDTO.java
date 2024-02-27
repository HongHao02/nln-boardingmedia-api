package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.BaiViet;
import com.b2012202.mxhtknt.Models.File;
import com.b2012202.mxhtknt.Models.Phong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaiVietDTO {
    private Long idBaiViet;
    private UserDTO user;
    private String description;
    private LocalDateTime published_at;
    private LocalDateTime last_update;
    private boolean lock;
    private Set<File> fileSet = new HashSet<>();
    private Set<Phong> phongSet = new HashSet<>();
    private int countLikes;
    private int countComments;

}
