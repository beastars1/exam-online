package com.lzq.exam.entity;

import com.lzq.exam.common.Question;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 主观题，记录所有的主观题及其详情
 *
 * @author beastars
 */
@Entity
@Table(name = "subjective_question")
@Data
public class SubjectiveQuestion extends Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subjective_id")
  private Long id;

  @Column
  private String course;

  @Column(name = "subjective_content")
  private String question;

  /** 参考答案 */
  @Column(name = "subjective_answer")
  private String answer;

  /** 分值 */
  @Column(name = "subjective_score")
  private Integer score;
}
