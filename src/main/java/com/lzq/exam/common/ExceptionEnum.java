package com.lzq.exam.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常枚举类
 *
 * @author beastars
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
  ADMIN_NOT_FIND(404, "管理员不存在"),
  STUDENT_NOT_FIND(404, "学生不存在"),
  TEACHER_NOT_FIND(404, "教师不存在"),
  EXAM_NOT_FIND(404, "考试不存在"),
  USER_NOT_FIND(500, "用户名或密码错误"),
  CHOICE_NOT_ENOUGH(500, "题库选择题数量不足"),
  FILL_NOT_ENOUGH(500, "题库填空题数量不足"),
  JUDGE_NOT_ENOUGH(500, "题库判断题数量不足"),
  ;
  private Integer code;
  private String msg;
}
