package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.ChiTietTuVan;
import com.b2012202.mxhtknt.Models.EmbeddedId.ChiTietTuVanID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChiTietTuVanRepository extends JpaRepository<ChiTietTuVan, ChiTietTuVanID> {
}
