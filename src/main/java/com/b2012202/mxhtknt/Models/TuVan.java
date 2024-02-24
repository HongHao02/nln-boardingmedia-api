package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TU_VAN")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuVan {
    @Id
    @SequenceGenerator(
            name = "tu_van_sequence",
            sequenceName = "tu_van_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tu_van_sequence"
    )
    @Column(name = "IDTV")
    private Long idTV;

    @ManyToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "IDBAIVIET", referencedColumnName = "IDBAIVIET")
    private BaiViet baiViet;

    @OneToMany(mappedBy = "tuVan", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ChiTietTuVan> chiTietTuVanSet= new HashSet<>();

    @Column(name = "VIEWED")
    private boolean viewed;

    //We have attribute 'thoiGianTuVan', cannot use ManyToMany
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "CHI_TIET_TU_VAN",
//            joinColumns = {@JoinColumn(name = "IDTV", referencedColumnName = "IDTV")},
//            inverseJoinColumns = {
//                    @JoinColumn(name = "IDNHATRO", referencedColumnName = "IDNHATRO"),
//                    @JoinColumn(name = "IDLAU", referencedColumnName = "IDLAU"),
//                    @JoinColumn(name = "IDPHONG", referencedColumnName = "IDPHONG")
//            }
//    )
//    @JsonManagedReference
//    private Set<Phong> phongTVSet = new HashSet<>();
}
