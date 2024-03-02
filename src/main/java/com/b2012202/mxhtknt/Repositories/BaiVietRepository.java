package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.BaiViet;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BaiVietRepository extends JpaRepository<BaiViet, Long> {
    @Query("SELECT bv FROM BaiViet bv WHERE bv.deleted != true")
    Page<BaiViet> findAll(Pageable pageable);

    @Query("SELECT bv FROM BaiViet bv WHERE bv.idBaiViet = :idBaiViet AND bv.deleted != true")
    Optional<BaiViet> findById(@Param("idBaiViet") Long idBaiViet);

    @Query("SELECT bv FROM BaiViet bv WHERE bv.user.id=:id AND bv.deleted != true")
    List<BaiViet> findAllBy_User_Id(@Param("id") Long id);
}
