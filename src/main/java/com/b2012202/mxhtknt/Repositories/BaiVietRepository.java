package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.BaiViet;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaiVietRepository extends JpaRepository<BaiViet, Long> {
    Page<BaiViet> findAll( Pageable pageable);
}
