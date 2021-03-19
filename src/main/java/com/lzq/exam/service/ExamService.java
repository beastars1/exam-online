package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.entity.Exam;
import com.lzq.exam.repository.ExamRepository;
import com.lzq.exam.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author beastars
 */
@Service
@Slf4j
public class ExamService {
  @Autowired
  private ExamRepository examRepository;

  /**
   * 分页查询考试信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  public PageResult<Exam> findExamByPage(Integer page, Integer size) {
    Page<Exam> pages = examRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "examId"));
    List<Exam> exams = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageSize = pages.getNumberOfElements();
    int pageNumber = pages.getNumber();
    log.info("[exam] query exams by page : {} ,size : {}", page + 1, size);
    return new PageResult<>(totalElements, exams, pageNumber, pageSize);
  }

  /**
   * 查询所有考试信息
   */
  public List<Exam> findAllExam() {
    log.info("[exam] query all exams");
    return examRepository.findAll();
  }

  /**
   * 根据考试编号查询考试信息
   */
  public Exam findExamById(Long examId) {
    Optional<Exam> optional = examRepository.findById(examId);
    log.info("[exam] query a exam whose id : {}", examId);
    return optional.orElseThrow(() -> new ExamException(ExceptionEnum.EXAM_NOT_FIND));
  }

  /**
   * 新增考试信息
   */
  @Transactional
  public void saveExam(Exam exam) {
    log.info("[exam] save a new exam whose id : {}", exam.getExamId());
    examRepository.save(exam);
  }

  /**
   * 根据 id 删除考试信息
   */
  @Transactional
  public void deleteExamById(Long examId) {
    log.info("[exam] delete a exam whose id : {}", examId);
    examRepository.deleteById(examId);
  }

  /**
   * 更新考试信息
   */
  @Transactional
  public void updateExam(Exam exam) {
    log.info("[exam] update a exam whose id : {}", exam.getExamId());
    examRepository.saveAndFlush(exam);
  }

  /**
   * 查询最后一条记录,返回给前端达到自增效果
   */
  public Exam findLastExam() {
    log.info("[exam] find last exam");
    return examRepository.findLastExam();
  }
}
