package com.kingpiggy.study.productordersystem.data.enumclass;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    /**
     * Common
     */
    ENTITY_NOT_FOUND("400", "C001", " Entity Not Found"),
    INVALID_INPUT_VALUE("400", "C002", " Invalid Input Value"),
    METHOD_NOT_ALLOWED("405", "C003", " Invalid Input Value"),

    INTERNAL_SERVER_ERROR("500", "C004", "Server Error"),
    INVALID_TYPE_VALUE("400", "C005", " Invalid Type Value"),

    ACCESS_DENIED("403", "C006", "Access denied"),
    CAN_NOT_DELETE_ENTITY("400", "C007", "Can't delete"),
    CONSTRAINT_VIOLATION("400", "COO8", "Constraint violation occurred"),
    HANDLE_ACCESS_DENIED("401", "C009", "Access is Denied"),
    SERVICE_NOT_AVAILABLE("400", "C010", "Service not available"),
    FILE_UPLOAD_FAIL("400", "C011", "File Upload failed"),
    DATA_ALREADY_EXIST("403", "C012", "Data Already registered"),

    /**
     * Order
     */
    SOLD_OUT("400", "O001", "Product sold out."),
    ORDER_REQUEST_EMPTY("400", "O002", "Empty order requested.");

    private final String code;
    private final String description;
    private final String resultCode;

    ErrorCode(final String resultCode, final String code, final String description) {
        this.resultCode = resultCode;
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return code;
    }

    public String getResultCode() {
        return resultCode;
    }
}
