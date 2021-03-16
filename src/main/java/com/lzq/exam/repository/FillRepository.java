package com.lzq.exam.repository;

import com.lzq.exam.entity.FillQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author beastars
 */
public interface FillRepository extends JpaRepository<FillQuestion, Long> {
  /**
   * 通过问题编号列表来查询问题信息
   */
  List<FillQuestion> findByIdIn(List<Long> ids);

  /**
   * 随机抽取题库中的题
   */
  @Query(nativeQuery = true,
    value = "select * from fill_question " +
      "where course = :course order by rand() desc limit :count")
  List<FillQuestion> findRandomFillByCourse(
    @Param("course") String course, @Param("count") Integer count
  );

  /**
   * 查询最后一个填空题
   */
  @Query(nativeQuery = true,
    value = "select * from fill_question order by fill_id desc limit 1")
  FillQuestion findLastFill();
}
