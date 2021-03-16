package com.lzq.exam.repository;

import com.lzq.exam.entity.JudgeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author beastars
 */
public interface JudgeRepository extends JpaRepository<JudgeQuestion, Long> {
  /**
   * 通过问题编号列表来查询问题信息
   */
  List<JudgeQuestion> findByIdIn(List<Long> ids);

  /**
   * 查询最后一个判断题
   */
  @Query(nativeQuery = true,
    value = "select * from judge_question order by judge_id desc limit 1")
  JudgeQuestion findLastJudge();

  /**
   * 随机抽取题库中的题
   */
  @Query(nativeQuery = true,
    value = "select * from judge_question " +
      "where course = :course order by rand() desc limit :count")
  List<JudgeQuestion> findRandomJudgeByCourse(
    @Param("course") String course, @Param("count") Integer count
  );
}
