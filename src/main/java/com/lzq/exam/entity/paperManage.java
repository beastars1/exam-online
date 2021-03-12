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
@Table(name = "paper_manage")
@Data
public class paperManage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "paper_id")
  private Long paperId;

  @Column(name = "question_type")
  private Integer type;

  @Column(name = "question_id")
  private Long questionId;
}
