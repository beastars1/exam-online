package com.lzq.exam.vo;

import lombok.Data;

/**
 * 每张试卷的问题数量分类视图模型
 *
 * @author beastars
 */
@Data
public class PaperItems {
  /** 试卷编号 */
  private Long paperId;
  /** 考试编号 */
  private Long examId;
  /** 试卷属于哪一个课程 */
  private String course;
  /** 这张试卷的选择题数量 */
  private Integer choiceCount;
  /** 这张试卷的填空题数量 */
  private Integer fillCount;
  /** 这张试卷的判断题数量 */
  private Integer judgeCount;
  /** 这张试卷的主管题数量 */
//  private Integer subjectiveCount;
}
