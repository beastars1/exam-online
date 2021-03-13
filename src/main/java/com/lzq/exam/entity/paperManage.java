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
@Table(name = "paper_manage")
@Data
public class paperManage {
  /** 试卷编号 */
  @Column(name = "paper_id")
  private Long paperId;

  /** 问题类型 */
  @Column(name = "question_type")
  private Integer type;

  /** 问题编号 */
  @Column(name = "question_id")
  private Long questionId;
}
