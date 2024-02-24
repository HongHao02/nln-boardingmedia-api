package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {
    List<BinhLuan> findByBaiViet_IdBaiViet(Long idBaiViet);

    @Query("SELECT bl FROM BinhLuan bl WHERE bl.baiViet.idBaiViet = :idBaiViet")
    List<BinhLuan> findByBaiVietId(@Param("idBaiViet") Long idBaiViet);
}
