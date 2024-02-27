package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.NhaTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NhaTroRepository extends JpaRepository<NhaTro, Long> {
    @Query("SELECT n FROM NhaTro n WHERE n.tenNhaTro LIKE %:tenNhaTro%")
    Page<NhaTro> findByTenContaining(@Param("tenNhaTro") String ten, Pageable pageable);

    @Query("SELECT nt FROM NhaTro nt " +
            "WHERE nt.tuyenDuong.tenDuong = :tenDuong " +
            "AND nt.xa.xaID.tenXa = :tenXa " +
            "AND nt.xa.xaID.tenHuyen = :tenHuyen " +
            "AND nt.xa.xaID.tenTinh = :tenTinh")
    Page<NhaTro> findByAbsoluteAddress(
            @Param("tenDuong") String tenDuong,
            @Param("tenXa") String tenXa,
            @Param("tenHuyen") String tenHuyen,
            @Param("tenTinh") String tenTinh,
            Pageable pageable
    );
}
