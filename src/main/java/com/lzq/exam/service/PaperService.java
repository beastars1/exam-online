package com.lzq.exam.service;

import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.entity.JudgeQuestion;
import com.lzq.exam.entity.Paper;
import com.lzq.exam.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author beastars
 */
@Service
@Slf4j
public class PaperService {
  @Autowired
  private PaperRepository paperRepository;

  @Autowired
  private FillService fillService;

  @Autowired
  private JudgeService judgeService;

  @Autowired
  private ChoiceService choiceService;

  /**
   * 根据试卷编号获取和该试卷关联的所有问题
   */
  public Map<Integer, List<?>> findPaperById(Long paperId) {
    List<Long> choiceIds = new ArrayList<>();
    List<Long> fillIds = new ArrayList<>();
    List<Long> judgeIds = new ArrayList<>();
    this.findPaperQuesById(paperId).forEach(p -> {
      if (p.getType() == 1) // 如果题型为1，说明是选择题，添加到选择题号列表
        choiceIds.add(p.getQuestionId());
      else if (p.getType() == 2) // 如果题型为2，说明是填空题，添加到填空题号列表
        fillIds.add(p.getQuestionId());
      else // 如果题型为3，说明是判断题，添加到判断题号列表
        judgeIds.add(p.getQuestionId());
    });
    // 返回一个map，key：题型，value：试卷中该题型的问题
    Map<Integer, List<?>> map = new HashMap<>();
    List<ChoiceQuestion> choice = choiceService.findByIdIn(choiceIds);
    List<FillQuestion> fill = fillService.findByIdIn(fillIds);
    List<JudgeQuestion> judge = judgeService.findByIdIn(judgeIds);
    map.put(1, choice);
    map.put(2, fill);
    map.put(3, judge);
    return map;
  }

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
