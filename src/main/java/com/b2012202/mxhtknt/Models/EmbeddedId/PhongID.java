package com.b2012202.mxhtknt.Models.EmbeddedId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhongID {
    private Long idNhaTro;
    private Long idLau;
    private Long idPhong;
}
