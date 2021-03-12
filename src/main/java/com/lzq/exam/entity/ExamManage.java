package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author beastars
 */
@Entity
@Table(name = "exam_manager")
@Data
public class ExamManage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exam_id")
  private Long examId;

  @Column(name = "exam_desc", nullable = false)
  private String description;

  @Column(name = "exam_course")
  private String course;

  @Column(name = "paper_id")
  private Long paperId;

  @Column(name = "exam_date")
  private String examDate;

  @Column(name = "exam_total_time")
  private Integer totalTime;

  @Column(name = "grade")
  private String grade;

  @Column(name = "term")
  private String term;

  @Column(name = "major")
  private String major;

  @Column(name = "department")
  private String department;

  @Column(name = "full_score")
  private Integer fullScore;

  @Column(name = "exam_notes", nullable = false)
  private String notes;
}
