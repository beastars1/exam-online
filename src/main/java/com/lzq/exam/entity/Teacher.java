package com.lzq.exam.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author beastars
 */
@Entity
@Table(name = "teacher")
@Data
public class Teacher {
  @Id
  @Column(name = "teacher_id")
  private Long id;

  @Column(name = "teacher_name")
  private String name;

  @Column(name = "department")
  private String department;

  @Column(name = "email")
  private String email;

  @Column(name = "pwd")
  private String pwd;

  @Column(name = "role")
  private Integer role;
}
