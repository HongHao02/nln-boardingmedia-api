package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.NhaTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NhaTroRepository extends JpaRepository<NhaTro, Long> {
    @Query("SELECT n FROM NhaTro n WHERE n.tenNhaTro LIKE %:tenNhaTro% AND n.deleted = false")
    Page<NhaTro> findByTenContaining(@Param("tenNhaTro") String tenNhaTro, Pageable pageable);

    @Query("SELECT nt FROM NhaTro nt " +
            "WHERE nt.tuyenDuong.tenDuong = :tenDuong " +
            "AND nt.xa.xaID.tenXa = :tenXa " +
            "AND nt.xa.xaID.tenHuyen = :tenHuyen " +
            "AND nt.xa.xaID.tenTinh = :tenTinh " +
            "AND nt.deleted = false ")
    Page<NhaTro> findByAbsoluteAddress(
            @Param("tenDuong") String tenDuong,
            @Param("tenXa") String tenXa,
            @Param("tenHuyen") String tenHuyen,
            @Param("tenTinh") String tenTinh,
            Pageable pageable
    );

    @Query("SELECT n FROM NhaTro n WHERE n.user.id=:id AND  n.deleted = false")
    List<NhaTro> findByUser_Id(Long id);
    @Query("SELECT nt FROM NhaTro nt WHERE nt.idNhaTro=:idNhaTro AND nt.deleted = false")
    Optional<NhaTro>findById(@Param("idNhaTro") Long idNhaTro);
    @Query("SELECT nt FROM NhaTro nt WHERE nt.deleted = false")
    List<NhaTro>findAll();

}
