package com.lzq.exam.repository;

import com.lzq.exam.entity.ChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author beastars
 */
public interface ChoiceRepository extends JpaRepository<ChoiceQuestion, Long> {
  /**
   * 通过问题编号列表来查询问题信息
   */
  List<ChoiceQuestion> findByIdIn(List<Long> ids);

  /**
   * 查询最后一个选择题
   */
  @Query(nativeQuery = true,
    value = "select * from choice_question order by choice_id desc limit 1")
  ChoiceQuestion findLastChoice();

  /**
   * 随机抽取题库中的题
   */
  @Query(nativeQuery = true,
    value = "select * from fill_question " +
      "where course = :course order by rand() desc limit :count")
  List<ChoiceQuestion> findRandomChoiceByCourse(
    @Param("course") String course, @Param("count") Integer count
  );
}
