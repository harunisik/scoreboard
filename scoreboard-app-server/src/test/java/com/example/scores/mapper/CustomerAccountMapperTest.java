package com.example.scores.mapper;

import static com.example.scores.ObjectFactory.buildScore;
import static com.example.scores.ObjectFactory.buildScoreCreateRequest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.model.Score;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerAccountMapperTest {

    ScoreMapperImpl customerAccountMapper = new ScoreMapperImpl();

    @Test
    public void toScoreResponse() {
        Score score = buildScore();
        ScoreResponse scoreResponse = customerAccountMapper.toScoreResponse(score);

        assertThat(scoreResponse.getId(), is(score.getId()));
        assertThat(scoreResponse.getTeamA(), is(score.getTeamA()));
        assertThat(scoreResponse.getScoreA(), is(score.getScoreA()));
        assertThat(scoreResponse.getTeamB(), is(score.getTeamB()));
        assertThat(scoreResponse.getScoreB(), is(score.getScoreB()));
    }

    @Test
    public void toScore() {
        ScoreCreateRequest scoreCreateRequest = buildScoreCreateRequest();
        Score score = customerAccountMapper.toScore(scoreCreateRequest);

        assertThat(score.getTeamA(), is(scoreCreateRequest.getTeamA()));
        assertThat(score.getScoreA(), is(scoreCreateRequest.getScoreA()));
        assertThat(score.getTeamB(), is(scoreCreateRequest.getTeamB()));
        assertThat(score.getScoreB(), is(scoreCreateRequest.getScoreB()));
    }
}