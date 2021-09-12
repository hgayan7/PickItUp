package com.project.pickItUp.exception;

import com.project.pickItUp.exception.type.ApiException;
import com.project.pickItUp.exception.type.ApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        log.info("Custom ApiRequestException exception triggered");
        ApiException apiException = new ApiException(e.getMessage());
        return new ResponseEntity<>(apiException,e.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException apiException = new ApiException(
                ex.getFieldErrors().stream()
                        .map(error -> new String(error.getObjectName() + "." + error.getField() + ":" + error.getDefaultMessage()))
                        .collect(Collectors.toList()).toString());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

}
