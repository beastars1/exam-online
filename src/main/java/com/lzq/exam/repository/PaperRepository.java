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

  /**
   * 来判断是不是已经存在要插入的问题
   */
  Paper findByPaperIdAndTypeAndQuestionId(Long paperId, Integer type, Long quesId);

  /**
   * 根据试卷编号删除试题
   */
  void deletePaperByPaperId(Long paperId);
}
