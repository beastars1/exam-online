package com.lzq.exam.service;

import com.lzq.exam.common.PrefixEnum;
import com.lzq.exam.entity.Score;
import com.lzq.exam.repository.ScoreRepository;
import com.lzq.exam.util.RedisUtils;
import com.lzq.exam.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author beastars
 */
@Service
@Slf4j
public class ScoreService {
  @Autowired
  private ScoreRepository scoreRepository;

  @Autowired
  private RedisUtils redisUtils;

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
    return scoreRepository.findAll(example);
  }

  /**
   * 分页查询对应学生id的所有成绩
   */
  public PageResult<Score> findPageByStudentId(Long studentId, Integer page, Integer size) {
    Score score = new Score();
    score.setStudentId(studentId);
    Example<Score> example = Example.of(score);
    Page<Score> pages = scoreRepository
      .findAll(example, PageRequest.of(page, size));
    List<Score> scores = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageNumber = pages.getNumber();
    log.info("[score] page query scores by student id : {}, page : {} ,size : {}", studentId, page + 1, size);
    return new PageResult<>(totalElements, scores, pageNumber, size);
  }

  /**
   * 分页查询对应考试id的所有成绩，并从大到小排列
   */
  public PageResult<Score> findPageByExamId(Long examId, Integer page, Integer size) {
    Score score = new Score();
    score.setExamId(examId);
    Example<Score> example = Example.of(score);
    Page<Score> pages = scoreRepository
      .findAll(example, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "score")));
    List<Score> scores = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageNumber = pages.getNumber();
    log.info("[score] page query scores by exam id : {}, page : {} ,size : {}", examId, page + 1, size);
    return new PageResult<>(totalElements, scores, pageNumber, size);
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

  public List<Score> findMaxScoreByExamIdGroupByStudentId(Long examId) {
    log.info("[score] query max scores by exam id : {}", examId);
    List<Score> list = new ArrayList<>();
    scoreRepository.findMaxScoreByExamIdGroupByStudentId(examId).forEach(score -> {
      Score s = new Score();
      s.setScore(score);
      list.add(s);
    });
    return list;
  }

  public Boolean haveExam(Long examId, Long studentId) {
    return scoreRepository.findExamIdsByStudentId(studentId).contains(examId);
  }

  /**
   * 新增一个分数信息
   */
  @Transactional
  public void saveScore(Score score) {
    log.info("[score] save a new score");
    scoreRepository.save(score);
    // 考试完毕，删除该考生在 redis 中的人脸缓存
    String key = PrefixEnum.EXAM_FACE.getPrefix() + score.getStudentId();
    redisUtils.delete(key);
  }
}
