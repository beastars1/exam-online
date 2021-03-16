package com.lzq.exam.controller;

import com.lzq.exam.entity.JudgeQuestion;
import com.lzq.exam.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/judge")
public class JudgeController {
  @Autowired
  private JudgeService judgeService;

  /**
   * 查询最后一个判断题
   */
  @GetMapping("/last")
  public ResponseEntity<JudgeQuestion> findLastJudge() {
    return ResponseEntity.ok(judgeService.findLastJudge());
  }

  /**
   * 添加一个判断题
   */
  @PostMapping
  public ResponseEntity<Void> saveJudge(@RequestBody JudgeQuestion judge) {
    judgeService.saveJudge(judge);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
