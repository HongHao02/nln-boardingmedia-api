package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.EmbeddedId.PhongID;
import com.b2012202.mxhtknt.Models.Phong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhongRepository extends JpaRepository<Phong, PhongID> {
    @Query(value = "SELECT NEXT VALUE FOR phong_sequence", nativeQuery = true)
    Long getNextSequenceValue();

    @Query("SELECT p FROM Phong p WHERE p.sttPhong = :sttPhong AND p.phongID.idNhaTro = :idNhaTro AND p.phongID.idLau = :idLau AND p.deleted = false AND p.lau.deleted= false AND p.lau.nhaTro.deleted=false")
    Optional<Phong> findByIDNhaTroAndIdLauAndSTTPhong(@Param("idNhaTro") Long idNhaTro, @Param("idLau") Long idLau,@Param("sttPhong") Integer sttPhong);

    @Query("SELECT p FROM Phong p WHERE p.phongID.idPhong= :idPhong AND p.deleted=false AND p.lau.deleted= false AND p.lau.nhaTro.deleted=false")
    Optional<Phong> findByIDPhong(@Param("idPhong") Long idPhong);

    @Query("SELECT p FROM Phong p WHERE p.phongID.idNhaTro = :idNhaTro AND p.deleted = false AND p.lau.deleted=false AND p.lau.nhaTro.deleted=false")
    List<Phong> findByPhongID_IdNhaTro(Long idNhaTro);

    @Query("SELECT p FROM Phong p WHERE p.phongID = :id AND p.tinhTrang = true and p.deleted=false AND p.lau.deleted= false AND p.lau.nhaTro.deleted=false")
    Optional<Phong> findById(@Param("id") PhongID id);

    @Query("SELECT p FROM Phong p WHERE p.phongID.idPhong = :idPhong AND p.deleted = false AND p.lau.deleted=false AND p.lau.nhaTro.deleted=false")
    Optional<Phong>findByPhongID_IdPhong(Long idPhong);
}
