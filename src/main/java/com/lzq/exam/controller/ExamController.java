package com.lzq.exam.controller;

import com.lzq.exam.entity.Exam;
import com.lzq.exam.service.ExamService;
import com.lzq.exam.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/exam")
public class ExamController {
  @Autowired
  private ExamService examService;

  /**
   * 分页查询考试信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  @GetMapping("/page")
  public ResponseEntity<PageResult<Exam>> findExamByPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "5") Integer size
  ) {
    return ResponseEntity.ok(examService.findExamByPage(page, size));
  }

  /**
   * 查询所有考试信息
   */
  @GetMapping
  public ResponseEntity<List<Exam>> findAllExam() {
    return ResponseEntity.ok(examService.findAllExam());
  }

  /**
   * 根据考试编号查询考试信息
   */
  @GetMapping("/{examId}")
  public ResponseEntity<Exam> findExamById(@PathVariable Long examId) {
    return ResponseEntity.ok(examService.findExamById(examId));
  }

  /**
   * 新增考试信息
   */
  @PostMapping
  public ResponseEntity<Void> saveExam(@RequestBody Exam exam) {
    examService.saveExam(exam);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据 id 删除考试信息
   */
  @DeleteMapping("/{examId}")
  public ResponseEntity<Void> deleteExamById(@PathVariable Long examId) {
    examService.deleteExamById(examId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 更新考试信息
   */
  @PutMapping
  public ResponseEntity<Void> updateExam(@RequestBody Exam exam) {
    examService.updateExam(exam);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 查询最后一条记录,返回给前端达到自增效果
   */
  @GetMapping("/last")
  public ResponseEntity<Exam> findLastExam() {
    return ResponseEntity.ok(examService.findLastExam());
  }

  /**
   * 根据考试id列表查询考试详情
   */
  @PostMapping("/exams")
  public ResponseEntity<List<Exam>> findExamsByIds(@RequestBody List<Long> ids) {
    return ResponseEntity.ok(examService.findExamsByIds(ids));
  }

  /**
   * 更新考试的分数信息
   */
  @PutMapping("/score")
  public ResponseEntity<Void> addFullScore(Integer score, Long examId) {
    examService.addFullScore(score, examId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
