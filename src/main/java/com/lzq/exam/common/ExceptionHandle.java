package com.lzq.exam.common;

import com.lzq.exam.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 自定义异常处理器
 *
 * @author beastars
 */
@ControllerAdvice
public class ExceptionHandle {
  @ExceptionHandler // 捕获抛出的 ExamException 异常
  public ResponseEntity<ExceptionResult> handleException(ExamException e) {
    return ResponseEntity.status(e.getExceptionEnum().getCode())
      .body(new ExceptionResult(e.getExceptionEnum()));
  }
}
