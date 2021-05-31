package com.example.scores.exception;

import lombok.Getter;

@Getter
public class ScoreBoardException extends RuntimeException {

    private ExceptionType exceptionType;

    public ScoreBoardException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

}
