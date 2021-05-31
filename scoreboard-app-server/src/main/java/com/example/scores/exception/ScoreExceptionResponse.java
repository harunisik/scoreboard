package com.example.scores.exception;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreExceptionResponse {

    private String errorCode;
    private String description;
    private Timestamp timestamp;

}
