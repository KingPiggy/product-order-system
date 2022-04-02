package com.kingpiggy.study.productordersystem.web.exception;

import com.kingpiggy.study.productordersystem.data.enumclass.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String description) {
        super(description, ErrorCode.ENTITY_NOT_FOUND);
    }
}
