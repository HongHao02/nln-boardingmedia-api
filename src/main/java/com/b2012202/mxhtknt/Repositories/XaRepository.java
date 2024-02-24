package com.b2012202.mxhtknt.Repositories;

import com.b2012202.mxhtknt.Models.EmbeddedId.XaID;
import com.b2012202.mxhtknt.Models.Xa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XaRepository extends JpaRepository<Xa, XaID> {
}
