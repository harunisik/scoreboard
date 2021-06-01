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
 * Created by Harun Isik on 31/05/21.
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
        logger.info("getting all scores from the db...");

        List<Score> scoreList = scoreRepository.findAll();
        logger.info("{} score(s) found in the db", scoreList.size());

        return scoreList.stream()
            .map(scoreMapper::toScoreResponse)
            .collect(toList());
    }

    @Transactional
    public ScoreResponse getScoreById(Long id) {
        logger.info("getting score with id {}...", id);

        Optional<Score> score = scoreRepository.findById(id);

        if (score.isPresent()) {
            logger.info("score found with id {}", id);
            return scoreMapper.toScoreResponse(score.get());
        }

        logger.info("score not found with id {}", id);
        throw new ScoreBoardException(SCORE_NOT_FOUND);
    }

    @Transactional
    public ScoreResponse createScore(ScoreCreateRequest scoreCreateRequest) {
        logger.info("creating a new score with the info {}...", scoreCreateRequest);

        Score score = scoreMapper.toScore(scoreCreateRequest);
        scoreRepository.save(score);
        logger.info("score created successfully with id {}", score.getId());
        return scoreMapper.toScoreResponse(score);
    }

    @Transactional
    public ScoreResponse updateScore(Long id, ScoreUpdateRequest scoreUpdateRequest) {
        logger.info("updating score with id {} and info {}...", id, scoreUpdateRequest);

        Optional<Score> scoreOpt = scoreRepository.findById(id);
        if (scoreOpt.isPresent()) {
            logger.info("score found with id {}", id);
            Score score = scoreOpt.get();
            score.setScoreA(scoreUpdateRequest.getScoreA());
            score.setScoreB(scoreUpdateRequest.getScoreB());
            scoreRepository.save(score);
            logger.info("score updated successfully with id {}", id);
            return scoreMapper.toScoreResponse(score);
        }

        throw new ScoreBoardException(SCORE_NOT_FOUND);
    }
}
