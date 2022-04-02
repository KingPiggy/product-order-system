package com.kingpiggy.study.productordersystem.web;

import com.kingpiggy.study.productordersystem.data.enumclass.ErrorCode;
import com.kingpiggy.study.productordersystem.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    /**
     * Exception 처리
     * Status : INTERNAL_SERVER_ERROR(500)
     * ResponseBody : 에러코드, 메시지 등을 담은 Header 전송
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Header handleException(Exception e) {
        log.error("handleException", e);
        return Header.ERROR(
            ErrorCode.INTERNAL_SERVER_ERROR.getResultCode(),
            ErrorCode.INTERNAL_SERVER_ERROR.getDescription(),
            e
        );
    }

    /**
     * BusinessException 처리
     * Status : INTERNAL_SERVER_ERROR(500)
     * ResponseBody : 에러코드, 메시지 등을 담은 Header 전송
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Header handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);

        final ErrorCode errorCode = e.getErrorCode();

        return Header.ERROR(
            errorCode.getResultCode(),
            errorCode.getDescription(),
            e
        );
    }

}
