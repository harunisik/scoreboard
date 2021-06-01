package com.example.scores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by Harun Isik on 31/05/21.
 */

@SpringBootApplication
@EntityScan(basePackageClasses = {ScoreBoardApplication.class})
public class ScoreBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreBoardApplication.class, args);
    }
}
