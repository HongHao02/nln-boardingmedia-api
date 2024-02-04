package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
