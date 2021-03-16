package com.lzq.exam.repository;

import com.lzq.exam.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author beastars
 */
public interface PaperRepository extends JpaRepository<Paper, Long> {
  /**
   * 根据试卷编号查询该试卷的所有问题
   */
  List<Paper> findByPaperId(Long paperId);
}
