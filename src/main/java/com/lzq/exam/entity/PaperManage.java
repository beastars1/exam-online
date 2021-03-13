package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 试卷管理，记录每一张试卷有什么问题
 *
 * @author beastars
 */
@Entity
@Table(name = "paper_manage")
@Data
public class PaperManage {
  /** 问题编号 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 试卷编号 */
  @Column(name = "paper_id")
  private Long paperId;

  /** 问题类型 */
  @Column(name = "question_type")
  private Integer type;

  /** 问题编号 */
  @Column(name = "question_id")
  private Long questionId;

  /**
   * 试卷中的问题集合
   * key：问题类型
   * value：该类型下的问题的编号集合
   */
//  private Map<Integer, List<Long>> questions;
}
