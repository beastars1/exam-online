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
@Table(name = "score_manage")
@Data
public class scoreManage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "socre_id")
  private Long scoreId;

  @Column(name = "exam_id")
  private Long examId;

  @Column(name = "student_id")
  private Long studentId;

  private String course;

  private Integer score;
}
