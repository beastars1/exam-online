package com.lzq.exam.repository;

import com.lzq.exam.entity.ScoreManage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface ScoreManageRepository extends JpaRepository<ScoreManage, Long> {
}
