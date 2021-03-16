package com.lzq.exam.repository;

import com.lzq.exam.entity.JudgeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author beastars
 */
public interface JudgeRepository extends JpaRepository<JudgeQuestion, Long> {
  /**
   * 通过问题编号列表来查询问题信息
   */
  List<JudgeQuestion> findByIdIn(List<Long> ids);
}
