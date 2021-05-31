package com.example.scores.controller;


import static com.example.scores.ObjectFactory.SCORE_A;
import static com.example.scores.ObjectFactory.SCORE_B;
import static com.example.scores.ObjectFactory.SCORE_ID;
import static com.example.scores.ObjectFactory.TEAM_A;
import static com.example.scores.ObjectFactory.TEAM_B;
import static com.example.scores.ObjectFactory.UPDATED_SCORE_A;
import static com.example.scores.ObjectFactory.UPDATED_SCORE_B;
import static com.example.scores.ObjectFactory.buildScoreCreateRequest;
import static com.example.scores.ObjectFactory.buildScoreResponse;
import static com.example.scores.ObjectFactory.buildScoreUpdateRequest;
import static com.example.scores.constant.UrlConstants.CREATE_SCORE_URL;
import static com.example.scores.constant.UrlConstants.GET_ALL_SCORE_URL;
import static com.example.scores.constant.UrlConstants.GET_SCORE_BY_ID_URL;
import static com.example.scores.constant.UrlConstants.UPDATE_SCORE_URL;
import static com.example.scores.exception.ExceptionType.SCORE_NOT_FOUND;
import static com.example.scores.util.JsonUtils.objectToJson;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.dto.ScoreUpdateRequest;
import com.example.scores.exception.GlobalExceptionHandler;
import com.example.scores.exception.ScoreBoardException;
import com.example.scores.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@ComponentScan({"com.example.*"})
public class CustomerAccountControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ScoreBoardController unit;

    @Mock
    private ScoreService scoreService;

    public static String id = "id";
    public static String teamA = "teamA";
    public static String scoreA = "scoreA";
    public static String teamB = "teamB";
    public static String scoreB = "scoreB";
    public static String errorCode = "errorCode";
    public static String description = "description";
    public static final String JSON_EXPRESSION = "$['%s']";
    public static final String JSON_EXPRESSION2 = "$.%s[%s]['%s']";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(unit)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    public void shouldGetAllScores() throws Exception {

        when(scoreService.getAllScores()).thenReturn(singletonList(buildScoreResponse(SCORE_A, SCORE_B)));

        mockMvc.perform(get(GET_ALL_SCORE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath(format(JSON_EXPRESSION2, "scoreList", 0, id)).value(SCORE_ID))
            .andExpect(jsonPath(format(JSON_EXPRESSION2, "scoreList", 0, teamA)).value(TEAM_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION2, "scoreList", 0, scoreA)).value(SCORE_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION2, "scoreList", 0, teamB)).value(TEAM_B))
            .andExpect(jsonPath(format(JSON_EXPRESSION2, "scoreList", 0, scoreB)).value(SCORE_B));
    }

    @Test
    public void shouldGetScoreById() throws Exception {

        when(scoreService.getScoreById(SCORE_ID)).thenReturn(of(buildScoreResponse(SCORE_A, SCORE_B)));

        mockMvc.perform(get(GET_SCORE_BY_ID_URL, "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath(format(JSON_EXPRESSION, id)).value(SCORE_ID))
            .andExpect(jsonPath(format(JSON_EXPRESSION, teamA)).value(TEAM_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION, scoreA)).value(SCORE_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION, teamB)).value(TEAM_B))
            .andExpect(jsonPath(format(JSON_EXPRESSION, scoreB)).value(SCORE_B));
    }

    @Test
    public void shouldThrowException_getScoreById() throws Exception {

        doThrow(new ScoreBoardException(SCORE_NOT_FOUND)).when(scoreService).getScoreById(any());

        this.mockMvc
            .perform(get(GET_SCORE_BY_ID_URL, "11"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath(format(JSON_EXPRESSION, errorCode)).value(SCORE_NOT_FOUND.getCode()))
            .andExpect(jsonPath(format(JSON_EXPRESSION, description)).value(SCORE_NOT_FOUND.getMessage()));
    }

    @Test
    public void shouldCreateScore() throws Exception {

        ScoreCreateRequest scoreCreateRequest = buildScoreCreateRequest();

        when(scoreService.createScore(scoreCreateRequest)).thenReturn(buildScoreResponse(SCORE_A, SCORE_B));

        mockMvc.perform(post(CREATE_SCORE_URL)
            .content(objectToJson(scoreCreateRequest))
            .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(format(JSON_EXPRESSION, id)).value(SCORE_ID))
            .andExpect(jsonPath(format(JSON_EXPRESSION, teamA)).value(TEAM_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION, scoreA)).value(SCORE_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION, teamB)).value(TEAM_B))
            .andExpect(jsonPath(format(JSON_EXPRESSION, scoreB)).value(SCORE_B));
    }

    @Test
    public void shouldUpdateScore() throws Exception {

        ScoreUpdateRequest scoreUpdateRequest = buildScoreUpdateRequest();
        ScoreResponse scoreResponse = buildScoreResponse(UPDATED_SCORE_A, UPDATED_SCORE_B);

        when(scoreService.updateScore(SCORE_ID, scoreUpdateRequest)).thenReturn(scoreResponse);

        mockMvc.perform(post(UPDATE_SCORE_URL, SCORE_ID)
            .content(objectToJson(scoreUpdateRequest))
            .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(format(JSON_EXPRESSION, id)).value(SCORE_ID))
            .andExpect(jsonPath(format(JSON_EXPRESSION, teamA)).value(TEAM_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION, scoreA)).value(UPDATED_SCORE_A))
            .andExpect(jsonPath(format(JSON_EXPRESSION, teamB)).value(TEAM_B))
            .andExpect(jsonPath(format(JSON_EXPRESSION, scoreB)).value(UPDATED_SCORE_B));
    }

    @Test
    public void shouldThrowException_updateScore() throws Exception {

        doThrow(new ScoreBoardException(SCORE_NOT_FOUND)).when(scoreService)
            .updateScore(anyLong(), any());

        this.mockMvc
            .perform(post(UPDATE_SCORE_URL, SCORE_ID)
                .content(objectToJson(buildScoreUpdateRequest()))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath(format(JSON_EXPRESSION, errorCode)).value(SCORE_NOT_FOUND.getCode()))
            .andExpect(jsonPath(format(JSON_EXPRESSION, description)).value(SCORE_NOT_FOUND.getMessage()));
    }

}
