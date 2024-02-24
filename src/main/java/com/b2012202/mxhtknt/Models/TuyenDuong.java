package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TUYEN_DUONG")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuyenDuong {
    @Id
    @Column(name = "TENDUONG")
    private String tenDuong;

    @ManyToMany(mappedBy = "tuyenDuongSet", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Xa> xaSet = new HashSet<>();

    @OneToMany(mappedBy = "tuyenDuong")
    @JsonBackReference
    private Set<NhaTro> nhaTroSet= new HashSet<>();
}
