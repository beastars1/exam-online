package com.lzq.exam.repository;

import com.lzq.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author beastars
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {
  @Query(nativeQuery = true,
    value = "select * from exam order by paper_id desc limit 1")
  Exam findLastExam();
}
