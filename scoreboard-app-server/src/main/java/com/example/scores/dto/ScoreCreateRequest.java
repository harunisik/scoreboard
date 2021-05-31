package com.example.scores.dto;

import javax.validation.constraints.NotNull;
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
public class ScoreCreateRequest {

    @NotNull
    private String teamA;

    @NotNull
    private Integer scoreA;

    @NotNull
    private String teamB;

    @NotNull
    private Integer scoreB;

}
