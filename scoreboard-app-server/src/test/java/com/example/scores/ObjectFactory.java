package com.example.scores;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.dto.ScoreUpdateRequest;
import com.example.scores.model.Score;

public class ObjectFactory {

    public static Long SCORE_ID = 1L;
    public static String TEAM_A = "Leeds United";
    public static Integer SCORE_A = 2;
    public static Integer UPDATED_SCORE_A = 3;
    public static String TEAM_B = "Manchester City";
    public static Integer SCORE_B = 1;
    public static Integer UPDATED_SCORE_B = 4;


    public static ScoreResponse buildScoreResponse(Integer scoreA, Integer scoreB) {
        return ScoreResponse.builder()
            .id(SCORE_ID)
            .teamA(TEAM_A)
            .scoreA(scoreA)
            .teamB(TEAM_B)
            .scoreB(scoreB)
            .build();
    }

    public static ScoreCreateRequest buildScoreCreateRequest() {
        return ScoreCreateRequest.builder()
            .teamA(TEAM_A)
            .scoreA(SCORE_A)
            .teamB(TEAM_B)
            .scoreB(SCORE_B)
            .build();
    }

    public static ScoreUpdateRequest buildScoreUpdateRequest() {
        return ScoreUpdateRequest.builder()
            .scoreA(UPDATED_SCORE_A)
            .scoreB(UPDATED_SCORE_B)
            .build();
    }

    public static Score buildScore() {
        return Score.builder()
            .id(SCORE_ID)
            .teamA(TEAM_A)
            .scoreA(SCORE_A)
            .teamB(TEAM_B)
            .scoreB(SCORE_B)
            .build();
    }
}
