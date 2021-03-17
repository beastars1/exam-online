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
    return ResponseEntity.ok(paperService.findPaperById(paperId));
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
