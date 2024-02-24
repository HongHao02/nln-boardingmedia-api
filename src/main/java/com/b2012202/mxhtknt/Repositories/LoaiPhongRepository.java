package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.LoaiPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {
    Optional<LoaiPhong> findByTenLoai(String tenLoai);
}
