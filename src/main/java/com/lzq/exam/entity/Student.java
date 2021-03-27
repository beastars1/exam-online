package com.lzq.exam.entity;

import com.lzq.exam.common.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生实体类
 *
 * @author beastars
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
@Data
public class Student extends Role {
  @Id
  @Column(name = "student_id")
  private Long id;

  @Column(name = "student_name")
  private String name;

  /** 学生照片 */
  @Column(name = "avatar")
  private String avatar;

  /** 学生专业 */
  @Column(name = "major")
  private String major;

  /** 学生年级 */
  @Column(name = "grade")
  private String grade;

  /** 学生班级 */
  @Column(name = "clazz")
  private String clazz;

  /** 学生院系 */
  @Column(name = "department")
  private String department;

  @Column(name = "email")
  private String email;

  @Column(name = "pwd")
  private String pwd;

  @Column(name = "role")
  private Integer role;
}
