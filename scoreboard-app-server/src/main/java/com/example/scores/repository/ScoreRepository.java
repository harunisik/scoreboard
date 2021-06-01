package com.example.scores.repository;

import com.example.scores.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harun Isik on 31/05/21.
 */

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
