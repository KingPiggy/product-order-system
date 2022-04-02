package com.kingpiggy.study.productordersystem.web.exception;

import com.kingpiggy.study.productordersystem.data.enumclass.ErrorCode;

public class SoldOutException extends BusinessException {
    public SoldOutException(String description) {
        super(description, ErrorCode.SOLD_OUT);
    }
}
