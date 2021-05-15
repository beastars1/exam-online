package com.lzq.exam.repository;

import com.lzq.exam.entity.SubjectiveQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 主观题暂未接入
 *
 * @author beastars
 */
public interface SubjectiveRepository extends JpaRepository<SubjectiveQuestion, Long> {
  /**
   * 通过问题编号列表来查询问题信息
   */
  List<SubjectiveQuestion> findByIdIn(List<Long> ids);
}
