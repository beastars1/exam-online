package com.lzq.exam.controller;

import com.lzq.exam.entity.ChoiceQuestion;
import com.lzq.exam.service.ChoiceService;
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
@RequestMapping("/choice")
public class ChoiceController {
  @Autowired
  private ChoiceService choiceService;

  /**
   * 查询最后一个选择题
   */
  @GetMapping("/last")
  public ResponseEntity<ChoiceQuestion> findLastChoice() {
    return ResponseEntity.ok(choiceService.findLastChoice());
  }

  /**
   * 新增一个选择题
   */
  @PostMapping
  public ResponseEntity<Void> saveChoice(@RequestBody ChoiceQuestion choice) {
    choiceService.saveChoice(choice);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
