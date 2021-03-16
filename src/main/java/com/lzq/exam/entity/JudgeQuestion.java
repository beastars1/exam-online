package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 判断题实体类
 *
 * @author beastars
 */
@Entity
@Table(name = "judge_question")
@Data
public class JudgeQuestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "judge_id")
  private Long id;

  @Column
  private String course;

  @Column(name = "judge_content")
  private String question;

  @Column(name = "judge_answer")
  private String answer;

  @Column
  private String analysis;

  @Column(name = "judge_score")
  private Integer score;
}
