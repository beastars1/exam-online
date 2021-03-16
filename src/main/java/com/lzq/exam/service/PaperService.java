package com.lzq.exam.service;

import com.lzq.exam.entity.Paper;
import com.lzq.exam.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author beastars
 */
@Service
@Slf4j
public class PaperService {
  @Autowired
  private PaperRepository paperRepository;

  /**
   * 查询所有试卷问题
   */
  public List<Paper> findAllPaperQuestion() {
    log.info("[paper] query all paper questions");
    return paperRepository.findAll();
  }

  /**
   * 根据试卷编号查询该试卷的所有问题
   */
  public List<Paper> findPaperQuesById(Long paperId) {
    log.info("[paper] query paper questions by paper id : {}", paperId);
    return paperRepository.findByPaperId(paperId);
  }

  /**
   * 保存一个关联了试卷的问题
   */
  public void savePaper(Paper paper) {
    log.info("[paper] save a paper belong to {}", paper.getPaperId());
    paperRepository.save(paper);
  }
}
