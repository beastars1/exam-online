package com.lzq.exam.controller;

import com.lzq.exam.entity.FillQuestion;
import com.lzq.exam.service.FillQuestionService;
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
@RequestMapping("/fill")
public class FillQuestionController {
  @Autowired
  private FillQuestionService fillService;

  /**
   * 查询最后一个填空题
   */
  @GetMapping("/last")
  public ResponseEntity<FillQuestion> findLastFill() {
    return ResponseEntity.ok(fillService.findLastFill());
  }

  /**
   * 添加填空题
   */
  @PostMapping
  public ResponseEntity<Void> saveFill(@RequestBody FillQuestion fill) {
    fillService.saveFill(fill);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
