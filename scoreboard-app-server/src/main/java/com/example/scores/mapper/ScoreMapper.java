package com.example.scores.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.model.Score;
import org.mapstruct.Mapper;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ScoreMapper {

    ScoreResponse toScoreResponse(final Score score);

    Score toScore(final ScoreCreateRequest scoreCreateRequest);
}
