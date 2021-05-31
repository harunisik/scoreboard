package com.example.scores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreResponse {

    private Long id;
    private String teamA;
    private Integer scoreA;
    private String teamB;
    private Integer scoreB;

}
