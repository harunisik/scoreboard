package com.example.scores.service;

import static com.example.scores.ObjectFactory.SCORE_A;
import static com.example.scores.ObjectFactory.SCORE_B;
import static com.example.scores.ObjectFactory.SCORE_ID;
import static com.example.scores.ObjectFactory.TEAM_A;
import static com.example.scores.ObjectFactory.TEAM_B;
import static com.example.scores.ObjectFactory.buildScore;
import static com.example.scores.ObjectFactory.buildScoreCreateRequest;
import static com.example.scores.ObjectFactory.buildScoreResponse;
import static com.example.scores.ObjectFactory.buildScoreUpdateRequest;
import static com.example.scores.exception.ExceptionType.SCORE_NOT_FOUND;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.dto.ScoreUpdateRequest;
import com.example.scores.exception.ScoreBoardException;
import com.example.scores.mapper.ScoreMapper;
import com.example.scores.model.Score;
import com.example.scores.repository.ScoreRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
@ComponentScan({"com.example.*"})
class CustomerTransactionServiceTest {

    @InjectMocks
    private ScoreService unit;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private ScoreMapper scoreMapper;

    @Test
    public void shouldGetAllScores() {

        Score score = buildScore();
        ScoreResponse scoreResponse = buildScoreResponse(SCORE_A, SCORE_B);

        when(scoreRepository.findAll()).thenReturn(singletonList(score));
        when(scoreMapper.toScoreResponse(score)).thenReturn((scoreResponse));

        List<ScoreResponse> scoreResponseList = unit.getAllScores();

        ScoreResponse result = scoreResponseList.get(0);

        verify(scoreRepository).findAll();
        verify(scoreMapper).toScoreResponse(score);

        assertThat(result.getId(), comparesEqualTo(SCORE_ID));
        assertThat(result.getTeamA(), comparesEqualTo(TEAM_A));
        assertThat(result.getScoreA(), comparesEqualTo(SCORE_A));
        assertThat(result.getTeamB(), comparesEqualTo(TEAM_B));
        assertThat(result.getScoreB(), comparesEqualTo(SCORE_B));
    }

    @Test
    public void shouldGetScoreById() throws Exception {

        Score score = buildScore();
        ScoreResponse scoreResponse = buildScoreResponse(SCORE_A, SCORE_B);

        when(scoreRepository.findById(SCORE_ID)).thenReturn(Optional.of(score));
        when(scoreMapper.toScoreResponse(score)).thenReturn((scoreResponse));

        Optional<ScoreResponse> scoreResponseOpt = unit.getScoreById(SCORE_ID);

        ScoreResponse result = scoreResponseOpt.get();

        verify(scoreRepository).findById(SCORE_ID);
        verify(scoreMapper).toScoreResponse(score);

        assertThat(result.getId(), comparesEqualTo(SCORE_ID));
        assertThat(result.getTeamA(), comparesEqualTo(TEAM_A));
        assertThat(result.getScoreA(), comparesEqualTo(SCORE_A));
        assertThat(result.getTeamB(), comparesEqualTo(TEAM_B));
        assertThat(result.getScoreB(), comparesEqualTo(SCORE_B));
    }

    @Test
    public void shouldThrowException_getScoreById() {

        ScoreBoardException exceptionThrown = assertThrows(ScoreBoardException.class,
            () -> unit.getScoreById(SCORE_ID));

        //THEN
        verify(scoreRepository).findById(SCORE_ID);

        assertThat(SCORE_NOT_FOUND.getMessage(), is(exceptionThrown.getMessage()));
        assertThat(SCORE_NOT_FOUND.getCode(), is(exceptionThrown.getExceptionType().getCode()));
    }

    @Test
    public void shouldCreateScore() {

        Score score = buildScore();
        ScoreCreateRequest scoreCreateRequest = buildScoreCreateRequest();
        ScoreResponse scoreResponse = buildScoreResponse(SCORE_A, SCORE_B);

        when(scoreMapper.toScore(scoreCreateRequest)).thenReturn(score);
        when(scoreMapper.toScoreResponse(score)).thenReturn(scoreResponse);

        ScoreResponse result = unit.createScore(scoreCreateRequest);

        verify(scoreMapper).toScore(scoreCreateRequest);
        verify(scoreRepository).save(score);
        verify(scoreMapper).toScoreResponse(score);

        assertThat(result.getId(), comparesEqualTo(SCORE_ID));
        assertThat(result.getTeamA(), comparesEqualTo(TEAM_A));
        assertThat(result.getScoreA(), comparesEqualTo(SCORE_A));
        assertThat(result.getTeamB(), comparesEqualTo(TEAM_B));
        assertThat(result.getScoreB(), comparesEqualTo(SCORE_B));
    }

    @Test
    public void shouldUpdateScore() throws Exception {

        Score score = buildScore();
        ScoreUpdateRequest scoreUpdateRequest = buildScoreUpdateRequest();
        ScoreResponse scoreResponse = buildScoreResponse(SCORE_A, SCORE_B);

        when(scoreRepository.findById(SCORE_ID)).thenReturn(Optional.of(score));
        when(scoreMapper.toScoreResponse(score)).thenReturn((scoreResponse));

        ScoreResponse result = unit.updateScore(SCORE_ID, scoreUpdateRequest);

        verify(scoreRepository).findById(SCORE_ID);
        verify(scoreRepository).save(score);
        verify(scoreMapper).toScoreResponse(score);

        assertThat(result.getId(), comparesEqualTo(SCORE_ID));
        assertThat(result.getTeamA(), comparesEqualTo(TEAM_A));
        assertThat(result.getScoreA(), comparesEqualTo(SCORE_A));
        assertThat(result.getTeamB(), comparesEqualTo(TEAM_B));
        assertThat(result.getScoreB(), comparesEqualTo(SCORE_B));
    }

    @Test
    public void shouldThrowException_updateScore() {

        ScoreBoardException exceptionThrown = assertThrows(ScoreBoardException.class,
            () -> unit.updateScore(SCORE_ID, buildScoreUpdateRequest()));

        //THEN
        verify(scoreRepository).findById(SCORE_ID);

        assertThat(SCORE_NOT_FOUND.getMessage(), is(exceptionThrown.getMessage()));
        assertThat(SCORE_NOT_FOUND.getCode(), is(exceptionThrown.getExceptionType().getCode()));
    }
}