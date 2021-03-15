package com.lzq.exam.repository;

import com.lzq.exam.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
