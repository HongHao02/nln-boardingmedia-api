package com.b2012202.mxhtknt.Models;

import com.b2012202.mxhtknt.Models.EmbeddedId.ChiTietTuVanID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "CHI_TIET_TU_VAN")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietTuVan {
    @EmbeddedId
    private ChiTietTuVanID chiTietTuVanID;

    @Column(name = "THOIGIANTUVAN")
    private LocalDateTime thoiGianTuVan = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));



    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "IDNHATRO", referencedColumnName = "IDNHATRO", insertable = false),
            @JoinColumn(name = "IDLAU", referencedColumnName = "IDLAU", insertable = false),
            @JoinColumn(name = "IDPHONG", referencedColumnName = "IDPHONG", insertable = false)
    })
    private Phong phong;

    @ManyToOne()
    @MapsId("idTV")
    @JoinColumns({
            @JoinColumn(name = "IDTV", referencedColumnName = "IDTV"),
    })
    @JsonBackReference
    private TuVan tuVan;

    @PrePersist
    public void ChiTietTuVanPre() {
        if (this.chiTietTuVanID == null) {
            chiTietTuVanID = new ChiTietTuVanID();
        }
        if (phong != null) {
            chiTietTuVanID.setIdNhaTro(phong.getPhongID().getIdNhaTro());
            chiTietTuVanID.setIdLau(phong.getPhongID().getIdLau());
            chiTietTuVanID.setIdPhong(phong.getPhongID().getIdPhong());
        }
    }
}
