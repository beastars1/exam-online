package com.lzq.exam.service;

import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.entity.JudgeQuestion;
import com.lzq.exam.vo.PageResult;
import com.lzq.exam.vo.QuestionView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author beastars
 */
@Service
@Slf4j
public class QuestionVoService {
  @Autowired
  private FillService fillService;

  @Autowired
  private JudgeService judgeService;

  @Autowired
  private ChoiceService choiceService;

  /**
   * 分页查询问题
   *
   * @param page 第几页，从0开始
   * @param size 每页大小
   */
  public PageResult<QuestionView> findAllQuestionsByPage(Integer page, Integer size) {
    // 所有问题的列表
    List<QuestionView> questions = new ArrayList<>();
    // 每个题库分别查询一部分
    Integer childSize = size / 3;

    // 查询填空题并转换成问题视图模型，添加到问题列表中
    PageResult<FillQuestion> fillByPage = fillService.findFillByPage(page, childSize);
    fillByPage.getRecords().forEach(fill -> {
      questions.add(new QuestionView(fill.getQuestion(), fill.getCourse(), fill.getScore(), "填空题"));
    });
    Long fillTotal = fillByPage.getTotal();

    // 查询选择题并转换成问题视图模型，添加到问题列表中
    PageResult<ChoiceQuestion> choiceByPage = choiceService.findChoiceByPage(page, childSize);
    choiceByPage.getRecords().forEach(choice -> {
      questions.add(new QuestionView(choice.getQuestion(), choice.getCourse(), choice.getScore(), "选择题"));
    });
    Long choiceTotal = choiceByPage.getTotal();

    // 查询判断题并转换成问题视图模型，添加到问题列表中
    PageResult<JudgeQuestion> judgeByPage = judgeService.findJudgeByPage(page, childSize);
    judgeByPage.getRecords().forEach(judge -> {
      questions.add(new QuestionView(judge.getQuestion(), judge.getCourse(), judge.getScore(), "判断题"));
    });
    Long judgeTotal = judgeByPage.getTotal();

    log.info("[answer] query all questions by page : {} ,size : {}", page + 1, size);

    // 总的条目数量
    Long total = fillTotal + choiceTotal + judgeTotal;
    // 拼装分页视图模型返回
    return new PageResult<>(total, questions, page, size);
  }
}
