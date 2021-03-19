package com.lzq.exam.service;

import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.repository.FillRepository;
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
public class FillService {
  @Autowired
  private FillRepository fillRepository;

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
    return fillRepository.findLastFill();
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
   * 通过问题编号列表来查询问题信息
   */
  public List<FillQuestion> findByIdIn(List<Long> ids) {
    return fillRepository.findByIdIn(ids);
  }

  /**
   * 添加填空题
   */
  @Transactional
  public void saveFill(FillQuestion fill) {
    log.info("[fill] save a new fill question");
    fillRepository.save(fill);
  }

  /**
   * 分页查询填空题信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  public PageResult<FillQuestion> findFillByPage(Integer page, Integer size) {
    Page<FillQuestion> pages = fillRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    List<FillQuestion> fills = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageNumber = pages.getNumber();
    log.info("[fill] query fills by page : {} ,size : {}", page + 1, size);
    return new PageResult<>(totalElements, fills, pageNumber, size);
  }
}
