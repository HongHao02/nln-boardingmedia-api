package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {
    @Query("SELECT bl FROM BinhLuan bl WHERE bl.baiViet.idBaiViet = :idBaiViet ORDER BY bl.thoiGianBL DESC")
    List<BinhLuan> findByBaiViet_IdBaiViet(@Param("idBaiViet") Long idBaiViet);

    @Query("SELECT bl FROM BinhLuan bl WHERE bl.baiViet.idBaiViet = :idBaiViet ORDER BY bl.thoiGianBL DESC")
    List<BinhLuan> findByBaiVietId(@Param("idBaiViet") Long idBaiViet);

    @Query("SELECT COUNT(bl) FROM BinhLuan bl WHERE bl.baiViet.idBaiViet = :idBaiViet ORDER BY bl.thoiGianBL DESC")
    int countCommentsByIdBaiViet(@Param("idBaiViet") Long idBaiViet);
}
