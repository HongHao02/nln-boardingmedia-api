package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.EmbeddedId.HuyenID;
import com.b2012202.mxhtknt.Models.Huyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HuyenRepository extends JpaRepository<Huyen, HuyenID> {
//    @Query("SELECT h FROM HUYEN h WHERE h.huyenID.tenHuyen = :tenHuyen AND h.huyenID.tenTinh = :tenTinh")
//    Optional<Huyen> findByHuyenID(@Param("tenHuyen") String tenHuyen, @Param("tenTinh") String tenTinh);
}
