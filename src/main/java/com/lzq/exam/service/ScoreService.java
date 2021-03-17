package com.lzq.exam.service;

import com.lzq.exam.entity.Score;
import com.lzq.exam.repository.ScoreRepository;
import com.lzq.exam.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class ScoreService {
  @Autowired
  private ScoreRepository scoreRepository;

  /**
   * 查询所有分数，并通过分数由高到低排序
   */
  public List<Score> findALlScore() {
    log.info("[score] query all scores");
    return scoreRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));
  }

  /**
   * 查询对应学生id的所有成绩
   */
  public List<Score> findByStudentId(Long studentId) {
    log.info("[score] query all scores of student id : {}", studentId);
    Score score = new Score();
    score.setStudentId(studentId);
    Example<Score> example = Example.of(score);
    return scoreRepository.findAll(example, Sort.by(Sort.Direction.DESC, "score"));
  }

  /**
   * 分页查询对应学生id的所有成绩
   */
  public PageResult<Score> findPageByStudentId(Long studentId, Integer page, Integer size) {
    Score score = new Score();
    score.setStudentId(studentId);
    Example<Score> example = Example.of(score);
    Page<Score> pages = scoreRepository
      .findAll(example, PageRequest.of(page, size, Sort.Direction.DESC, "score"));
    List<Score> scores = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageSize = pages.getNumberOfElements();
    int pageNumber = pages.getNumber();
    log.info("[score] query scores by page : {} ,size : {}", page + 1, size);
    return new PageResult<>(totalElements, scores, pageNumber, pageSize);
  }

  /**
   * 根据考试编号查询每个学生的分数
   */
  public List<Score> findByExamId(Long examId) {
    log.info("[score] query scores by exam id : {}", examId);
    Score score = new Score();
    score.setExamId(examId);
    Example<Score> example = Example.of(score);
    return scoreRepository.findAll(example, Sort.by(Sort.Direction.ASC, "studentId"));
  }

  /**
   * 新增一个分数信息
   */
  @Transactional
  public void saveScore(Score score) {
    log.info("[score] save a new score");
    scoreRepository.save(score);
  }
}
