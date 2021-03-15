package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 考试管理，记录每一场考试的详情
 *
 * @author beastars
 */
@Entity
@Table(name = "exam")
@Data
public class Exam {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exam_id")
  private Long examId;

  /** 考试描述 */
  @Column(name = "exam_desc")
  private String description;

  /** 考试课程 */
  @Column(name = "exam_course")
  private String course;

  /** 试卷编号 */
  @Column(name = "paper_id")
  private Long paperId;

  /** 考试日期 */
  @Column(name = "exam_date")
  private String examDate;

  /** 考试时长 */
  @Column(name = "exam_total_time")
  private Integer totalTime;

  /** 考试学期 */
  @Column(name = "term")
  private String term;

  /** 考试年级 */
  @Column(name = "grade")
  private String grade;

  /** 考试专业 */
  @Column(name = "major")
  private String major;

  /** 考试院系 */
  @Column(name = "department")
  private String department;

  /** 考试总分 */
  @Column(name = "full_score")
  private Integer fullScore;

  /** 考试要求 */
  @Column(name = "exam_notes")
  private String notes;
}
