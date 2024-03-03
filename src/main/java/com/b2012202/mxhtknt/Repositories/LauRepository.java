package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.EmbeddedId.LauID;
import com.b2012202.mxhtknt.Models.Lau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LauRepository extends JpaRepository<Lau, LauID> {
    @Query("SELECT l FROM Lau l WHERE l.sttLau=:sttLau AND l.deleted= false AND l.nhaTro.deleted = false")
    Optional<Lau> findBySttLau(Integer sttLau);
    @Query("SELECT l FROM Lau l WHERE l.lauID = :Id AND l.deleted=false AND l.nhaTro.deleted = false")
    Optional<Lau> findById(@Param("Id") LauID Id);
    @Query("SELECT l FROM Lau l WHERE l.sttLau = :sttLau AND l.lauID.idNhaTro = :idNhaTro AND l.nhaTro.deleted = false AND l.deleted=false")
    Optional<Lau> findBySTTLauAndIdNhaTro(@Param("sttLau") Integer sttLau, @Param("idNhaTro") Long idNhaTro);
    @Query(value = "SELECT NEXT VALUE FOR lau_sequence", nativeQuery = true)
    Long getNextSequenceValue();
    @Query("SELECT l FROM Lau l WHERE  l.deleted=false AND l.nhaTro.deleted= false")
    List<Lau>findAll();
}
