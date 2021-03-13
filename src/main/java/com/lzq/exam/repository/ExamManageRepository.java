package com.lzq.exam.repository;

import com.lzq.exam.entity.ExamManage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface ExamManageRepository extends JpaRepository<ExamManage, Long> {
}
