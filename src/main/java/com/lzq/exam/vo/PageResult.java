package com.lzq.exam.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页查询结果视图模型
 *
 * @author beastars
 */
@Data
public class PageResult<T> {
  private Long total; // 总条数
  private List<T> records; // 当前页数据
  private int current; // 当前页页码
  private int size; // 当前页大小

  public PageResult() {
  }

  public PageResult(Long total, List<T> records, int current, int size) {
    this.total = total;
    this.records = records;
    this.current = current + 1;
    this.size = size;
  }
}
