package com.lzq.exam.vo;

import com.lzq.exam.common.ExceptionEnum;
import lombok.Data;

/**
 * 自定义异常视图模型
 *
 * @author beastars
 */
@Data
public class ExceptionResult {
  private Integer status;
  private String msg;
  private Long timestamp;

  public ExceptionResult(ExceptionEnum exceptionEnum){
    this.status = exceptionEnum.getCode();
    this.msg = exceptionEnum.getMsg();
    this.timestamp = System.currentTimeMillis();
  }
}
