package com.project.pickItUp.exception.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequestException extends RuntimeException{
    private String message;
    private HttpStatus status;
}
