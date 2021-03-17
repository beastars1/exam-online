package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 试卷问题管理，记录所有和试卷有关联的问题
 *
 * @author beastars
 */
@Entity
@Table(name = "paper")
@Data
public class Paper {
  /** 问题编号 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 试卷编号 */
  @Column(name = "paper_id")
  private Long paperId;

  /** 问题类型：1，选择题；2，填空题；3，判断题；4，主观题 */
  @Column(name = "question_type")
  private Integer type;

  /** 问题编号 */
  @Column(name = "question_id")
  private Long questionId;

  public Paper() {
  }

  public Paper(Long paperId, Integer type, Long questionId) {
    this.paperId = paperId;
    this.type = type;
    this.questionId = questionId;
  }

  public Paper(Long id, Long paperId, Integer type, Long questionId) {
    this.id = id;
    this.paperId = paperId;
    this.type = type;
    this.questionId = questionId;
  }
}
