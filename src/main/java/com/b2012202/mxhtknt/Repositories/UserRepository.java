package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.BaiViet;
import com.b2012202.mxhtknt.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u JOIN u.likedBaiVietSet b WHERE b.idBaiViet = :idBaiViet")
    int countLikesByIdBaiViet(@Param("idBaiViet") Long idBaiViet);
//    Wrong query
//    @Query("SELECT b FROM User u JOIN u.likedBaiVietSet b WHERE b.id = :id")
    @Query("SELECT b FROM BaiViet b JOIN b.nguoiDungLikedSet u WHERE u.id = :id")
    Set<BaiViet> findLikedBaiVietSetById(@Param("id") Long id);

}
