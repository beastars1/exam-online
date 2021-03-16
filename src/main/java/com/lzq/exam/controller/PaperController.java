package com.lzq.exam.controller;

import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.entity.JudgeQuestion;
import com.lzq.exam.entity.Paper;
import com.lzq.exam.service.ChoiceService;
import com.lzq.exam.service.FillService;
import com.lzq.exam.service.JudgeService;
import com.lzq.exam.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/paper")
@Slf4j
public class PaperController {
  @Autowired
  private PaperService paperService;

  @Autowired
  private FillService fillService;

  @Autowired
  private JudgeService judgeService;

  @Autowired
  private ChoiceService choiceService;

  /**
   * 获取所有的关联了试卷的问题
   */
  @GetMapping
  public ResponseEntity<List<Paper>> findAllPaperQuestion() {
    return ResponseEntity.ok(paperService.findAllPaperQuestion());
  }

  /**
   * 根据试卷编号获取和该试卷关联的所有问题
   */
  @GetMapping("/{paperId}")
  public ResponseEntity<Map<Integer, List<?>>> findPaperById(@PathVariable Long paperId) {
    List<Long> choiceIds = new ArrayList<>();
    List<Long> fillIds = new ArrayList<>();
    List<Long> judgeIds = new ArrayList<>();
    paperService.findPaperQuesById(paperId).forEach(p -> {
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
    return ResponseEntity.ok(map);
  }

  /**
   * 保存一个关联了试卷的问题
   */
  @PostMapping
  public ResponseEntity<Void> savePaper(@RequestBody Paper paper) {
    paperService.savePaper(paper);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
