package com.lzq.exam.entity;

import com.lzq.exam.common.Role;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 教师实体类
 *
 * @author beastars
 */
@Entity
@Table(name = "teacher")
@Data
public class Teacher extends Role {
  @Id
  @Column(name = "teacher_id")
  private Long id;

  @Column(name = "teacher_name")
  private String name;

  /** 教师院系 */
  @Column(name = "department")
  private String department;

  @Column(name = "email")
  private String email;

  @Column(name = "pwd")
  private String pwd;

  @Column(name = "role")
  private Integer role;
}
