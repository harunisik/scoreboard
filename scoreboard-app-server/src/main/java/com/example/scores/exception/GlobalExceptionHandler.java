package com.example.scores.exception;

import static com.example.scores.exception.ExceptionType.INTERNAL_ERROR;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ScoreBoardException.class)
    @Order(HIGHEST_PRECEDENCE)
    @ResponseBody
    public ResponseEntity<ScoreExceptionResponse> handleException(ScoreBoardException exception) {
        log.error("Error: ", exception);
        ExceptionType exceptionType = exception.getExceptionType();
        ScoreExceptionResponse scoreExceptionResponse = convertToResponse(exceptionType);
        return new ResponseEntity<>(scoreExceptionResponse, exceptionType.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ScoreExceptionResponse> handleAllExceptions(Exception exception) {
        log.error("Error: ", exception);
        ScoreExceptionResponse scoreExceptionResponse = convertToResponse(INTERNAL_ERROR);
        return new ResponseEntity<>(scoreExceptionResponse, INTERNAL_SERVER_ERROR);
    }

    private ScoreExceptionResponse convertToResponse(ExceptionType exceptionType) {
        return ScoreExceptionResponse.builder()
            .errorCode(exceptionType.getCode())
            .description(exceptionType.getMessage())
            .timestamp(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    }

}