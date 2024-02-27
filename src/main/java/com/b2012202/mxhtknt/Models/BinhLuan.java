package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="BINH_LUAN")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BinhLuan {
    @Id
    @SequenceGenerator(
            name = "binh_luan_sequence",
            sequenceName = "binh_luan_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "binh_luan_sequence"
    )
    @Column(name = "IDBL")
    private Long idBL;

    @ManyToOne
    @JoinColumn(name = "ID")
    private User user;

    @ManyToOne
    //If you adhered (fetch= FetchType.LAZY) the error 'getOutputStream() has already called for this response will occur'
    @JsonIgnore
    @JoinColumn(name = "IDBAIVIET")
    private BaiViet baiViet;

    @Column(name = "NOIDUNG")
    private String noiDung;

    @Column(name = "THOIGIANBL")
    private LocalDateTime thoiGianBL;
}
