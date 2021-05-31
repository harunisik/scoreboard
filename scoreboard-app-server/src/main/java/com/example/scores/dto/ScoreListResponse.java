package com.example.scores.dto;

import java.util.List;
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
public class ScoreListResponse {

    private List<ScoreResponse> scoreList;

}
