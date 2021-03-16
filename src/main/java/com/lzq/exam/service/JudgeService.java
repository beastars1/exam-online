package com.lzq.exam.service;

import com.lzq.exam.entity.JudgeQuestion;
import com.lzq.exam.repository.JudgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author beastars
 */
@Service
@Slf4j
public class JudgeService {
  @Autowired
  private JudgeRepository judgeRepository;

  /**
   * 通过问题编号列表来查询问题信息
   */
  public List<JudgeQuestion> findByIdIn(List<Long> ids) {
    return judgeRepository.findByIdIn(ids);
  }

  /**
   * 查询所有判断题
   */
  public List<JudgeQuestion> findAllJudge() {
    log.info("[judge] query all judge questions");
    return judgeRepository.findAll();
  }

  /**
   * 查询最后一个判断题
   */
  public JudgeQuestion findLastJudge() {
    log.info("[judge] query the last judge question");
    return judgeRepository.findLastJudge();
  }

  /**
   * 随机抽取题库中的题
   *
   * @param course 科目
   * @param count  抽取的数量
   */
  public List<JudgeQuestion> findRandomJudgeByCourse(String course, Integer count) {
    log.info("[judge] random query {} {} judge questions", count, course);
    return judgeRepository.findRandomJudgeByCourse(course, count);
  }

  /**
   * 添加一个判断题
   */
  public void saveJudge(JudgeQuestion judge) {
    log.info("[judge] save a new judge question");
    judgeRepository.save(judge);
  }
}
