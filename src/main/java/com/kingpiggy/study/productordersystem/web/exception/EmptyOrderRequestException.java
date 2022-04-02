package com.kingpiggy.study.productordersystem.web.exception;

import com.kingpiggy.study.productordersystem.data.enumclass.ErrorCode;

public class EmptyOrderRequestException extends BusinessException {
    public EmptyOrderRequestException(String description) {
        super(description, ErrorCode.ORDER_REQUEST_EMPTY);
    }
}
