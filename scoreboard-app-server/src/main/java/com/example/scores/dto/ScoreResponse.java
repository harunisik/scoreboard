package com.example.scores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Harun Isik on 31/05/21.
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
