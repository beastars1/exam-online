package com.lzq.exam.repository;

import com.lzq.exam.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author beastars
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {
  @Query(nativeQuery = true,
    value = "select max(score) from score where :examId group by student_id")
  List<Integer> findMaxScoreByExamIdGroupByStudentId(@Param("examId") Long examId);

  @Query("select score.examId from Score score where score.studentId = :studentId")
  List<Long> findExamIdsByStudentId(Long studentId);
}
