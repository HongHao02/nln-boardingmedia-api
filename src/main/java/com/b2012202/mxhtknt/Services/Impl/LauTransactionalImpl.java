package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Services.LauTransactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class LauTransactionalImpl{
    @PersistenceContext
    private final EntityManager entityManager;

    public Long getNextSequenceValue() {
        return (Long) entityManager.createNativeQuery("SELECT NEXT VALUE FOR lau_sequence").getSingleResult();
    }
}
