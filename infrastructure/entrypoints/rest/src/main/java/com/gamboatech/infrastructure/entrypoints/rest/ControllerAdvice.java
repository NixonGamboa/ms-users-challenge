package com.gamboatech.infrastructure.entrypoints.rest;

import com.gamboatech.domain.commons.BusinessException;
import com.gamboatech.domain.commons.ErrorCodes;
import com.gamboatech.infrastructure.entrypoints.rest.dto.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.EnumMap;

import static com.gamboatech.domain.commons.ErrorCodes.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDto> businessExceptionHandler(BusinessException ex){
        ErrorDto error = new ErrorDto(ex.getErrorCode().name(),ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(error,httpStatusCodeMapper(ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Valor invalido para el parametro '%s': %s", ex.getName(), ex.getValue());
        ErrorDto error = new ErrorDto(BAD_REQUEST.name(), errorMessage);
        return new ResponseEntity<>(error, httpStatusCodeMapper(BAD_REQUEST));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .reduce((message1, message2) -> message1 + ", " + message2)
                .orElse(ex.getMessage());
        ErrorDto error = new ErrorDto(BAD_REQUEST.name(), errorMessage);
        log.error(ex.getMessage());

        return new ResponseEntity<>(error, httpStatusCodeMapper(BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce((message1, message2) -> message1 + ", " + message2)
                .orElse(ex.getMessage());
        ErrorDto error = new ErrorDto(BAD_REQUEST.name(), errorMessage);
        log.error(ex.getMessage());

        return new ResponseEntity<>(error, httpStatusCodeMapper(BAD_REQUEST));
    }

    private HttpStatus httpStatusCodeMapper(ErrorCodes errorCode){

        EnumMap<ErrorCodes, HttpStatus> errorCodeMapper = new EnumMap<>(ErrorCodes.class);
        errorCodeMapper.put(NOT_FOUND,HttpStatus.NOT_FOUND);
        errorCodeMapper.put(BAD_REQUEST,HttpStatus.BAD_REQUEST);
        errorCodeMapper.put(DUPLICATED, HttpStatus.CONFLICT);

        return errorCodeMapper.getOrDefault(errorCode,INTERNAL_SERVER_ERROR);
    }
}
