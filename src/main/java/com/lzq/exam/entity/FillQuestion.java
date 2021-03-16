package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 填空题，记录所有的填空题及其详情
 *
 * @author beastars
 */
@Entity
@Table(name = "fill_question")
@Data
public class FillQuestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "fill_id")
  private Long id;

  @Column
  private String course;

  @Column(name = "fill_content")
  private String question;

  @Column(name = "fill_answer")
  private String answer;

  @Column
  private String analysis;

  @Column(name = "fill_score")
  private Integer score;
}
