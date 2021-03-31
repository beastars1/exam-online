package com.lzq.exam.repository;

import com.lzq.exam.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author beastars
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
