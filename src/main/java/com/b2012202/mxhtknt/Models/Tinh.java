package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TINH")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tinh {

//    @SequenceGenerator(
//            name = "tinh_sequence",
//            sequenceName = "tinh_sequence",
//            allocationSize = 1 //increment by 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "tinh_sequence"
//    )
//    @Column(name = "IDTINH")
//    private String idTinh;
    @Id
    @Column(name = "TENTINH")
    private String tenTinh;

    @OneToMany(mappedBy = "tinh", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Huyen> huyenSet= new HashSet<>();
}
