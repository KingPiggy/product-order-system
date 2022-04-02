package com.kingpiggy.study.productordersystem.web.exception;

import com.kingpiggy.study.productordersystem.data.enumclass.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(String description, ErrorCode errorCode) {
        super(description);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
