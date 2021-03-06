package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 成绩管理，记录所有的成绩
 *
 * @author beastars
 */
@Entity
@Table(name = "score")
@Data
public class Score {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "score_id")
  private Long scoreId;

  /** 考试编号，该成绩是哪一个考试的 */
  @Column(name = "exam_id")
  private Long examId;

  /** 学号，该成绩属于哪一个学生 */
  @Column(name = "student_id")
  private Long studentId;

  /** 课程，该成绩属于哪一门课 */
  @Column(nullable = false)
  private String course;

  /** 该次考试的名称 */
  @Column
  private String description;

  @Column
  private Integer score;

  @Column
  private String answerDate;
}
