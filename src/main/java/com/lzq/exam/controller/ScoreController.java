package com.lzq.exam.controller;

import com.lzq.exam.entity.Score;
import com.lzq.exam.service.ScoreService;
import com.lzq.exam.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/score")
public class ScoreController {
  @Autowired
  private ScoreService scoreService;

  /**
   * 查询所有分数，并通过分数由高到低排序
   */
  @GetMapping
  public ResponseEntity<List<Score>> findALlScore() {
    return ResponseEntity.ok(scoreService.findALlScore());
  }

  /**
   * 查询对应学生id的所有成绩
   */
  @GetMapping("/{studentId}")
  public ResponseEntity<List<Score>> findByStudentId(@PathVariable("studentId") Long studentId) {
    return ResponseEntity.ok(scoreService.findByStudentId(studentId));
  }

  /**
   * 分页查询对应学生id的所有成绩
   */
  @GetMapping("/page/{studentId}")
  public ResponseEntity<PageResult<Score>> findPageByStudentId(
    @PathVariable("studentId") Long studentId,
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "5") Integer size
  ) {
    return ResponseEntity.ok(scoreService.findPageByStudentId(studentId, page, size));
  }

  /**
   * 根据考试编号查询每个学生的分数
   */
  @GetMapping("/e/{examId}")
  public ResponseEntity<List<Score>> findByExamId(@PathVariable("examId") Long examId) {
    return ResponseEntity.ok(scoreService.findByExamId(examId));
  }

  /**
   * 根据考试编号查询每个学生的分数
   */
  @GetMapping("/m/{examId}")
  public ResponseEntity<List<Score>> findMaxScoreByExamId(@PathVariable("examId") Long examId) {
    return ResponseEntity.ok(scoreService.findMaxScoreByExamIdGroupByStudentId(examId));
  }

  /**
   * 根据学号和考试编号判断是否已经考过该次考试
   */
  @PostMapping("/d")
  public ResponseEntity<Boolean> haveExam(Long examId, Long studentId) {
    return ResponseEntity.ok(scoreService.haveExam(examId, studentId));
  }

  /**
   * 新增一个分数信息
   */
  @PostMapping
  public ResponseEntity<Void> saveScore(@RequestBody Score score) {
    scoreService.saveScore(score);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
