package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NHA_TRO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenNhaTro", "tenHuyen", "tenTinh"})
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NhaTro {
    @Id
    @SequenceGenerator(
            name = "nhatro_sequence",
            sequenceName = "nhatro_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "nhatro_sequence"
    )
    @Column(name = "IDNHATRO")
    private Long idNhaTro;

    @Column(name = "TENNHATRO")
    private String tenNhaTro;

    @ManyToOne
    @JoinColumn(name = "TENDUONG", referencedColumnName = "TENDUONG")
    @JsonManagedReference
    private TuyenDuong tuyenDuong;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "TENTINH", referencedColumnName = "TENTINH"),
            @JoinColumn(name = "TENHUYEN", referencedColumnName = "TENHUYEN"),
            @JoinColumn(name = "TENXA", referencedColumnName = "TENXA")
    })
    @JsonManagedReference
    private Xa xa;

    @ManyToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(mappedBy = "nhaTro")
    @JsonManagedReference
    private Set<Lau> lauSet= new HashSet<>();



}
