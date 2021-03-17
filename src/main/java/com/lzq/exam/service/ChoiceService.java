package com.lzq.exam.service;

import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.repository.ChoiceRepository;
import com.lzq.exam.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

  /**
   * 分页查询选择题信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  public PageResult<ChoiceQuestion> findChoiceByPage(Integer page, Integer size) {
    Page<ChoiceQuestion> pages = choiceRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    List<ChoiceQuestion> choices = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageSize = pages.getNumberOfElements();
    int pageNumber = pages.getNumber();
    log.info("[choice] query choices by page : {} ,size : {}", page + 1, size);
    return new PageResult<>(totalElements, choices, pageNumber, pageSize);
  }
}
