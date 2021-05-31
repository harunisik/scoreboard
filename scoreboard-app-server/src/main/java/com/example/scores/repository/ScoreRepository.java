package com.example.scores.repository;

import com.example.scores.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
