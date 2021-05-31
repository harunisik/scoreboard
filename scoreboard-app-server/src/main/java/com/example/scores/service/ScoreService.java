package com.example.scores.service;

import static com.example.scores.exception.ExceptionType.SCORE_NOT_FOUND;
import static java.util.stream.Collectors.toList;

import com.example.scores.dto.ScoreCreateRequest;
import com.example.scores.dto.ScoreResponse;
import com.example.scores.dto.ScoreUpdateRequest;
import com.example.scores.exception.ScoreBoardException;
import com.example.scores.mapper.ScoreMapper;
import com.example.scores.model.Score;
import com.example.scores.repository.ScoreRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Transactional
    public List<ScoreResponse> getAllScores() {

        List<Score> scoreList = scoreRepository.findAll();

        return scoreList.stream()
            .map(scoreMapper::toScoreResponse)
            .collect(toList());
    }

    @Transactional
    public Optional<ScoreResponse> getScoreById(Long id) throws Exception {

        Optional<Score> score = scoreRepository.findById(id);

        if (score.isPresent()) {
            ScoreResponse scoreResponse = scoreMapper.toScoreResponse(score.get());
            return Optional.of(scoreResponse);
        }

        throw new ScoreBoardException(SCORE_NOT_FOUND);
    }

    @Transactional
    public ScoreResponse createScore(ScoreCreateRequest scoreCreateRequest) {
        Score score = scoreMapper.toScore(scoreCreateRequest);
        scoreRepository.save(score);
        return scoreMapper.toScoreResponse(score);
    }

    @Transactional
    public ScoreResponse updateScore(Long id, ScoreUpdateRequest scoreUpdateRequest) throws Exception {
        Optional<Score> scoreOpt = scoreRepository.findById(id);
        if (scoreOpt.isPresent()) {
            Score score = scoreOpt.get();
            score.setScoreA(scoreUpdateRequest.getScoreA());
            score.setScoreB(scoreUpdateRequest.getScoreB());
            scoreRepository.save(score);
            return scoreMapper.toScoreResponse(score);
        }

        throw new ScoreBoardException(SCORE_NOT_FOUND);
    }
}
