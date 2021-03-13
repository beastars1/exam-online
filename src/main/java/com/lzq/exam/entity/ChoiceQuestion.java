package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 选择题，记录所有的选择题及详情
 *
 * @author beastars
 */
@Entity
@Table(name = "choice_question")
@Data
public class ChoiceQuestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "choice_id")
  private Long id;

  /** 该题属于哪一门课 */
  @Column
  private String course;

  /** 问题的详细内容 */
  @Column(name = "choice_content")
  private String question;

  /** 选项A */
  @Column(name = "option_a")
  private String optionA;

  /** 选项B */
  @Column(name = "option_b")
  private String optionB;

  /** 选项C */
  @Column(name = "option_c")
  private String optionC;

  /** 选项D */
  @Column(name = "option_d")
  private String optionD;

  /** 正确答案 */
  @Column(name = "choice_answer")
  private String answer;

  /** 题目解析 */
  @Column
  private String analysis;

  /** 题目分值 */
  @Column(name = "choice_score")
  private Integer score;
}
