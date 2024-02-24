package com.b2012202.mxhtknt.Models.EmbeddedId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChiTietTuVanID implements Serializable {
    private Long idTV;
    private Long idNhaTro;
    private Long idLau;
    private Long idPhong;

}
