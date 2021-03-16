package com.lzq.exam.service;

import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.repository.ChoiceRepository;
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
public class ChoiceService {
  @Autowired
  private ChoiceRepository choiceRepository;

  /**
   * 通过问题编号列表来查询问题信息
   */
  public List<ChoiceQuestion> findByIdIn(List<Long> ids) {
    return choiceRepository.findByIdIn(ids);
  }

  /**
   * 查询所有选择题
   */
  public List<ChoiceQuestion> findAllChoice() {
    log.info("[choice] query all choice questions");
    return choiceRepository.findAll();
  }

  /**
   * 查询最后一个选择题
   */
  public ChoiceQuestion findLastChoice() {
    log.info("[choice] query the last choice question");
    return choiceRepository.findLastChoice();
  }

  /**
   * 随机抽取题库中的题
   *
   * @param course 科目
   * @param count  抽取的数量
   */
  public List<ChoiceQuestion> findRandomChoiceByCourse(String course, Integer count) {
    log.info("[choice] random query {} {} choice questions", count, course);
    return choiceRepository.findRandomChoiceByCourse(course, count);
  }

  /**
   * 新增一个选择题
   */
  @Transactional
  public void saveChoice(ChoiceQuestion choice) {
    log.info("[choice] save a new choice");
    choiceRepository.save(choice);
  }
}
