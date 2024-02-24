package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.EmbeddedId.LauID;
import com.b2012202.mxhtknt.Models.Lau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LauRepository extends JpaRepository<Lau, LauID> {
    Optional<Lau> findBySttLau(Integer sttLau);

    @Query("SELECT l FROM Lau l WHERE l.sttLau = :sttLau AND l.lauID.idNhaTro = :idNhaTro")
    Optional<Lau> findBySTTLauAndIdNhaTro(@Param("sttLau") Integer sttLau, @Param("idNhaTro") Long idNhaTro);
    @Query(value = "SELECT NEXT VALUE FOR lau_sequence", nativeQuery = true)
    Long getNextSequenceValue();
}
