package com.b2012202.mxhtknt.Models;

import com.b2012202.mxhtknt.Models.EmbeddedId.PhongID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PHONG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phong {
    @EmbeddedId
    private PhongID phongID;

    @ManyToOne
    @JoinColumn(name = "IDLOAI")
    @JsonManagedReference
    private LoaiPhong loaiPhong;

    //For generate auto idPhong
    // ADD this statement in sql script: CREATE SEQUENCE phong_sequence START WITH 1 INCREMENT BY 1;
    @SequenceGenerator(
            name = "phong_sequence",
            sequenceName = "phong_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "phong_sequence"
    )
    private Long sequenceValue;

    @Column(name = "STTPHONG")
    private Integer sttPhong;
    @Column(name = "GIAPHONG")
    private Double giaPhong;
    @Column(name = "TINHTRANG")
    private boolean tinhTrang;

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "IDNHATRO", referencedColumnName = "IDNHATRO", insertable = false),
            @JoinColumn(name = "IDLAU", referencedColumnName = "IDLAU", insertable = false)
    })
    @JsonBackReference
    private Lau lau;

    @ManyToMany(mappedBy = "phongSet", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<BaiViet> baiVietSet = new HashSet<>();

}
