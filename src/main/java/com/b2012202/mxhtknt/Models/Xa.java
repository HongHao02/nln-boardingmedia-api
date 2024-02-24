package com.b2012202.mxhtknt.Models;

import com.b2012202.mxhtknt.Models.EmbeddedId.XaID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "XA")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Xa {
    @EmbeddedId
    private XaID xaID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "TENTINH", referencedColumnName = "TENTINH", insertable = false),
            @JoinColumn(name = "TENHUYEN", referencedColumnName = "TENHUYEN", insertable = false)
    })
    @JsonBackReference
    private Huyen huyen;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "TUYENDUONG_XA",
            joinColumns = {
                    @JoinColumn(name = "TENTINH", referencedColumnName = "TENTINH"),
                    @JoinColumn(name = "TENHUYEN", referencedColumnName = "TENHUYEN"),
                    @JoinColumn(name = "TENXA", referencedColumnName = "TENXA")
            },
            inverseJoinColumns = {@JoinColumn(name = "TENDUONG", referencedColumnName = "TENDUONG")})
    @JsonManagedReference
    private Set<TuyenDuong> tuyenDuongSet = new HashSet<>();

    @OneToMany(mappedBy = "xa")
    @JsonBackReference
    private Set<NhaTro> nhaTroSet = new HashSet<>();

    public String toString() {
        return "\"Xa\" + Ten Xa: " + this.xaID.getTenXa()
                + "__Ten huyen from Huyen attribute: " + this.huyen.getHuyenID().getTenHuyen()
                + "__Ten huyen from XaID: " + this.xaID.getTenHuyen()
                + "__Ten tinh from XaID: " + this.xaID.getTenTinh();
    }
    @PrePersist
    @PreUpdate
    private void updateXaID() {
        if (this.huyen != null) {
            this.xaID.setTenHuyen(this.huyen.getHuyenID().getTenHuyen());
            this.xaID.setTenTinh(this.huyen.getHuyenID().getTenTinh());
        }
    }

}
