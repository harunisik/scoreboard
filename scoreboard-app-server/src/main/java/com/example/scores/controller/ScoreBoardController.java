package com.example.scores.controller;

import static com.example.scores.constant.UrlConstants.CREATE_SCORE_URL;
import static com.example.scores.constant.UrlConstants.GET_ALL_SCORE_URL;
import static com.example.scores.constant.UrlConstants.GET_SCORE_BY_ID_URL;
import static com.example.scores.constant.UrlConstants.UPDATE_SCORE_URL;
import static org.springframework.http.HttpStatus.CREATED;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreListResponse;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.dto.ScoreUpdateRequest;
import com.example.scores.service.ScoreService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harun Isik on 31/05/21.
 */

@RestController
@RequestMapping
public class ScoreBoardController {

    private static final Logger logger = LoggerFactory.getLogger(ScoreBoardController.class);

    @Autowired
    private ScoreService scoreService;

    @GetMapping(GET_ALL_SCORE_URL)
    public ResponseEntity<ScoreListResponse> getAllScores() {
        logger.info("get scores called.");

        List<ScoreResponse> scoreList = scoreService.getAllScores();
        ScoreListResponse scoreListResponse = ScoreListResponse.builder()
            .scoreList(scoreList)
            .build();

        return ResponseEntity.ok().body(scoreListResponse);
    }

    @GetMapping(GET_SCORE_BY_ID_URL)
    public ResponseEntity<ScoreResponse> getScoreById(@Valid @PathVariable(value = "id") String id) throws Exception {
        logger.info("get score by id called.");
        Optional<ScoreResponse> scoreResponseOpt = scoreService.getScoreById(Long.parseLong(id));
        return ResponseEntity.ok().body(scoreResponseOpt.orElse(null));
    }

    @PostMapping(CREATE_SCORE_URL)
    public ResponseEntity<ScoreResponse> createScore(@Valid @RequestBody ScoreCreateRequest scoreCreateRequest) {
        logger.info("create score called.");
        ScoreResponse scoreResponse = scoreService.createScore(scoreCreateRequest);
        return ResponseEntity.status(CREATED).body(scoreResponse);
    }

    @PostMapping(UPDATE_SCORE_URL)
    public ResponseEntity<ScoreResponse> updateScore(
        @Valid @PathVariable(value = "id") String id,
        @Valid @RequestBody ScoreUpdateRequest scoreUpdateRequest) throws Exception {
        logger.info("create score called.");

        ScoreResponse scoreResponse = scoreService.updateScore(Long.parseLong(id), scoreUpdateRequest);
        return ResponseEntity.ok().body(scoreResponse);
    }
}