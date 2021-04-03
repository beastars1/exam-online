package com.lzq.exam.repository;

import com.lzq.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author beastars
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {
  @Query(nativeQuery = true,
    value = "select * from exam order by paper_id desc limit 1")
  Exam findLastExam();

  /**
   * 更新考试总分数
   */
  @Modifying
  @Query("update Exam exam set exam.fullScore = :score where exam.examId = :examId")
  void updateScoreByExamId(@Param("score") Integer score, @Param("examId") Long examId);

  /**
   * 更新比并加上考试总分数
   */
  @Modifying
  @Query("update Exam exam set exam.fullScore = exam.fullScore + :score where exam.examId = :examId")
  void addScoreByExamId(@Param("score") Integer score, @Param("examId") Long examId);
}
