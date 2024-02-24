package com.b2012202.mxhtknt.Services;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LauTransactional {
    Long getNextSequenceValue();
}
