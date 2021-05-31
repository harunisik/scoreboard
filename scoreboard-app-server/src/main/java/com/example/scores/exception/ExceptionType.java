package com.example.scores.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionType {

    INTERNAL_ERROR("100", "An internal server error occurred", INTERNAL_SERVER_ERROR),
    SCORE_NOT_FOUND("101", "Score does not exist.", NOT_FOUND);

    private String code;
    private String message;
    private HttpStatus httpStatus;
    
}
