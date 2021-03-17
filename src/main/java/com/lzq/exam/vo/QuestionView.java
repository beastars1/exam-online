package com.lzq.exam.vo;

import lombok.Data;

/**
 * 问题视图模型
 *
 * @author beastars
 */
@Data
public class QuestionView {
  /** 问题内容 */
  private String question;
  /** 问题所属课程 */
  private String course;
  /** 问题分值 */
  private Integer score;
  /** 问题类型 */
  private String type;

  public QuestionView() {
  }

  public QuestionView(String question, String course, Integer score, String type) {
    this.question = question;
    this.course = course;
    this.score = score;
    this.type = type;
  }
}
