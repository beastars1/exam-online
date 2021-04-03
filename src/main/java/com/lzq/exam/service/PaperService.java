package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.entity.JudgeQuestion;
import com.lzq.exam.entity.Paper;
import com.lzq.exam.repository.PaperRepository;
import com.lzq.exam.vo.PaperItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

  @Autowired
  private ExamService examService;

  /**
   * 根据试卷编号获取和该试卷关联的所有问题
   */
  public Map<Integer, List<?>> findPaperById(Long paperId) {
    log.info("[paper] query all questions on a paper id : {}", paperId);
    List<Long> choiceIds = new ArrayList<>();
    List<Long> fillIds = new ArrayList<>();
    List<Long> judgeIds = new ArrayList<>();
//    List<Long> subjectiveIds = new ArrayList<>();
    this.findPaperQuesById(paperId).forEach(p -> {
      if (p.getType() == 1) // 如果题型为1，说明是选择题，添加到选择题号列表
        choiceIds.add(p.getQuestionId());
      else if (p.getType() == 2) // 如果题型为2，说明是填空题，添加到填空题号列表
        fillIds.add(p.getQuestionId());
//      else if (p.getType() == 4) // 如果题型为4，说明是主观题，添加到主观题号列表
//        subjectiveIds.add(p.getQuestionId());
      else // 如果题型为3，说明是判断题，添加到判断题号列表
        judgeIds.add(p.getQuestionId());
    });
    // 返回一个map，key：题型，value：试卷中该题型的问题
    Map<Integer, List<?>> map = new HashMap<>();
    List<ChoiceQuestion> choice = choiceService.findByIdIn(choiceIds);
    List<FillQuestion> fill = fillService.findByIdIn(fillIds);
    List<JudgeQuestion> judge = judgeService.findByIdIn(judgeIds);
//    List<JudgeQuestion> subjective = judgeService.findByIdIn(subjectiveIds);
    map.put(1, choice);
    map.put(2, fill);
    map.put(3, judge);
//    map.put(4, subjective);
    return map;
  }

  /**
   * 从题库中抽取问题，并组装到一张试卷中
   */
  @Transactional
  public void assemblePaper(PaperItems items) {
    Long examId = items.getExamId();
    // 这张试卷的总分
    AtomicReference<Integer> fullScore = new AtomicReference<>(0);
    // 获取各种题型的问题数量
    Integer choiceCount = items.getChoiceCount();
    Integer fillCount = items.getFillCount();
    Integer judgeCount = items.getJudgeCount();

    log.info("[assembly] assemble a paper, choice count : {}, fill count : {}, judge count : {}",
      choiceCount, fillCount, judgeCount);

    // 获取试卷id
    Long paperId = items.getPaperId();
    // 随机获取选择题
    List<ChoiceQuestion> choices = choiceService.findRandomChoiceByCourse(items.getCourse(), choiceCount);
    if (choices.size() < choiceCount)
      throw new ExamException(ExceptionEnum.CHOICE_NOT_ENOUGH);
    // 将获取的问题和试卷问题表paper关联
    choices.forEach(choice -> {
      fullScore.updateAndGet(v -> v + choice.getScore());
      if (paperRepository.findByPaperIdAndTypeAndQuestionId(paperId, 1, choice.getId()) == null)
        this.savePaper(new Paper(paperId, 1, choice.getId()));
    });

    // 随机获取填空题
    List<FillQuestion> fills = fillService.findRandomFillByCourse(items.getCourse(), fillCount);
    if (fills.size() < fillCount)
      throw new ExamException(ExceptionEnum.FILL_NOT_ENOUGH);
    fills.forEach(fill -> {
      fullScore.updateAndGet(v -> v + fill.getScore());
      if (paperRepository.findByPaperIdAndTypeAndQuestionId(paperId, 2, fill.getId()) == null)
        this.savePaper(new Paper(paperId, 2, fill.getId()));
    });

    // 随机获取判断题
    List<JudgeQuestion> judges = judgeService.findRandomJudgeByCourse(items.getCourse(), judgeCount);
    if (judges.size() < judgeCount)
      throw new ExamException(ExceptionEnum.JUDGE_NOT_ENOUGH);
    // 将获取的问题和试卷问题表paper关联
    judges.forEach(judge -> {
      fullScore.updateAndGet(v -> v + judge.getScore());
      if (paperRepository.findByPaperIdAndTypeAndQuestionId(paperId, 3, judge.getId()) == null)
        this.savePaper(new Paper(paperId, 3, judge.getId()));
    });

    // 更新总分
    examService.updateFullScore(fullScore.get(), examId);
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
