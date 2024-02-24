package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.TuVan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TuVanRepository extends JpaRepository<TuVan, Long> {
    @Query("SELECT tv FROM TuVan tv WHERE tv.baiViet.user.id= :id AND tv.viewed = false")
    List<TuVan> findByBaiViet_user_id(@Param("id") Long id);

    @Query("SELECT tv FROM TuVan tv WHERE tv.user.username= :username AND tv.baiViet.idBaiViet = :idBaiViet")
    Optional<TuVan> findByPhongAndId(@Param("username") String username, @Param("idBaiViet") Long idBaiViet);

}
