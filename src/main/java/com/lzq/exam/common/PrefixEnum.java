package com.lzq.exam.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * redis 缓存前缀
 *
 * @author beastars
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PrefixEnum {
  EXAM_FACE("exam:face:")
  ;
  private String prefix;
}
