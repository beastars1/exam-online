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
  private Long totalPage; // 总页数
  private List<T> items; // 当前页数据

  public PageResult() {
  }

  public PageResult(Long total, List<T> items) {
    this.total = total;
    this.items = items;
  }

  public PageResult(Long total, Long totalPage, List<T> items) {
    this.total = total;
    this.totalPage = totalPage;
    this.items = items;
  }
}
