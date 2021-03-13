package com.lzq.exam.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常实体类
 *
 * @author beastars
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExamException extends RuntimeException{
  private ExceptionEnum exceptionEnum;
}
