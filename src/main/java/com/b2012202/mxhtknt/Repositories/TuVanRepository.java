package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.TuVan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TuVanRepository extends JpaRepository<TuVan, Long> {
    @Query("SELECT tv FROM TuVan tv WHERE tv.baiViet.user.id= :id AND tv.baiViet.deleted != true")
    List<TuVan> findByBaiViet_user_id(@Param("id") Long id);

    @Query("SELECT tv FROM TuVan tv WHERE tv.user.username= :username AND tv.baiViet.idBaiViet = :idBaiViet AND tv.baiViet.deleted != true")
    Optional<TuVan> findByPhongAndId(@Param("username") String username, @Param("idBaiViet") Long idBaiViet);

    /**
     * count tu van not view for CHU_TRO
     */
    @Query("SELECT COUNT(tv) FROM TuVan tv WHERE tv.baiViet.user.id= :id AND tv.baiViet.deleted != true AND tv.viewed= false")
    int countTuVanById(@Param("id") Long id);
    /**
     * count viewed tu van for KHACH_THUE
     */
    @Query("SELECT COUNT(tv) FROM TuVan tv WHERE tv.user.id= :id AND tv.baiViet.deleted != true AND tv.viewed= true")
    int countViewedByUserId(@Param("id") Long id);
//    @Query("SELECT tv FROM TuVan tv WHERE tv.user.id= :id AND tv.baiViet.deleted != true")
    @Query("SELECT tv FROM TuVan tv JOIN FETCH tv.chiTietTuVanSet c WHERE tv.user.id = :id AND tv.baiViet.deleted != true ORDER BY c.thoiGianTuVan DESC")
    List<TuVan> findByUserId(@Param("id") Long id);
}
