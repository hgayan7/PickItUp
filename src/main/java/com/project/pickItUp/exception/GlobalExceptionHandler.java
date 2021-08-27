package com.project.pickItUp.exception;

import com.project.pickItUp.exception.type.ApiException;
import com.project.pickItUp.exception.type.ApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        log.info("Custom ApiRequestException exception triggered");
        ApiException apiException = new ApiException(e.getMessage());
        return new ResponseEntity<>(apiException,e.getStatus());
    }
}
