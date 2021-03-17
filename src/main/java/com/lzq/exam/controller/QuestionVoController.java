package com.lzq.exam.controller;

import com.lzq.exam.service.QuestionVoService;
import com.lzq.exam.vo.PageResult;
import com.lzq.exam.vo.QuestionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/question")
public class QuestionVoController {
  @Autowired
  private QuestionVoService questionVoService;

  /**
   * 分页查询问题
   *
   * @param page 第几页，从0开始
   * @param size 每页大小
   */
  @GetMapping
  public ResponseEntity<PageResult<QuestionView>> findAllQuestionsByPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "5") Integer size
  ) {
    return ResponseEntity.ok(questionVoService.findAllQuestionsByPage(page, size));
  }
}
