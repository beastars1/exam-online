package com.lzq.exam.service;

import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.repository.FillQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author beastars
 */
@Service
@Slf4j
public class FillQuestionService {
  @Autowired
  private FillQuestionRepository fillRepository;

  /**
   * 查询所有的填空题
   */
  public List<FillQuestion> findAllFill() {
    log.info("[fill] query all fill questions");
    return fillRepository.findAll();
  }

  /**
   * 查询最后一个填空题
   */
  public FillQuestion findLastFill() {
    log.info("[fill] query the last fill question");
    return fillRepository.findLastExam();
  }

  /**
   * 随机抽取题库中的题
   *
   * @param course 科目
   * @param count  抽取的数量
   */
  public List<FillQuestion> findRandomFillByCourse(String course, Integer count) {
    log.info("[fill] random query {} {} fill questions", count, course);
    return fillRepository.findRandomFillByCourse(course, count);
  }

  /**
   * 添加填空题
   */
  @Transactional
  public void saveFill(FillQuestion fill) {
    log.info("[fill] save a new fill question");
    fillRepository.save(fill);
  }
}
