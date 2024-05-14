package com.gamboatech.domain.commons;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCodes errorCode;
    private final Object body;

    public BusinessException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.body = null;
    }
}
