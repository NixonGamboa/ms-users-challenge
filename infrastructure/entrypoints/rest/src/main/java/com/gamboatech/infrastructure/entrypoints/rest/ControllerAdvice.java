package com.gamboatech.infrastructure.entrypoints.rest;

import com.gamboatech.domain.commons.BusinessException;
import com.gamboatech.domain.commons.ErrorCodes;
import com.gamboatech.infrastructure.entrypoints.rest.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.EnumMap;

import static com.gamboatech.domain.commons.ErrorCodes.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDto> businessExceptionHandler(BusinessException ex){
        ErrorDto error = ErrorDto.builder()
                .code(ex.getErrorCode().name())
                .message(ex.getMessage())
                .build();
        log.error(ex.getMessage());
        return new ResponseEntity<>(error,httpStatusCodeMapper(ex.getErrorCode()));
    }

    private HttpStatus httpStatusCodeMapper(ErrorCodes errorCode){

        EnumMap<ErrorCodes, HttpStatus> errorCodeMapper = new EnumMap<>(ErrorCodes.class);
        errorCodeMapper.put(NOT_FOUND,HttpStatus.NOT_FOUND);

        return errorCodeMapper.getOrDefault(errorCode,INTERNAL_SERVER_ERROR);
    }
}
