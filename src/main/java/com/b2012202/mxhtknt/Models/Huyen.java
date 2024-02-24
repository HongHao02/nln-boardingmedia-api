package com.b2012202.mxhtknt.Models;

import com.b2012202.mxhtknt.Models.EmbeddedId.HuyenID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="HUYEN")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Huyen {

    @EmbeddedId
    private HuyenID huyenID;

    @MapsId("tenTinh")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENTINH")
    //Ngan khong cho con goi nguoc lai cha (truy van thanh vong lap)
    @JsonBackReference
    private Tinh tinh;

    @OneToMany(mappedBy = "huyen")
    @JsonManagedReference
    private Set<Xa> xaSet= new HashSet<>();


    public String toString(){
        return "\"Huyen\" + Ten huyen: " + this.huyenID.getTenHuyen()
                + "__Ten tinh from tinh attribute: "+ this.tinh.getTenTinh()
                +"__Ten tinh from huyenID: "+ this.huyenID.getTenTinh();
    }
}
