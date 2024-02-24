package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="LOAI_PHONG")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoaiPhong {
    @Id
    @SequenceGenerator(
            name = "loaiphong_sequence",
            sequenceName = "loaiphong_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loaiphong_sequence"
    )
    @Column(name = "IDLOAI")
    private Long idLoai;

    @Column(name = "TENLOAI")
    private String tenLoai;

    @OneToMany(mappedBy = "loaiPhong")
    @JsonBackReference
    private Set<Phong> phongSet= new HashSet<>();
}
