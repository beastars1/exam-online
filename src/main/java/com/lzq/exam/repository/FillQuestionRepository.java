package com.lzq.exam.repository;

import com.lzq.exam.entity.FillQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author beastars
 */
public interface FillQuestionRepository extends JpaRepository<FillQuestion, Long> {
  @Query(nativeQuery = true,
    value = "select * from fill_question" +
      "where course = :course order by rand() desc limit :count")
  List<FillQuestion> findRandomFillByCourse(@Param("course") String course, @Param("pageNo") Integer count);

  @Query(nativeQuery = true, value = "select * from fill_question order by fill_id desc limit 1")
  FillQuestion findLastExam();
}
