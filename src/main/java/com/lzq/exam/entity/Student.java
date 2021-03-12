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
@Table(name = "student")
@Data
public class Student {
  @Id
  @Column(name = "student_id")
  private Long id;

  @Column(name = "student_name")
  private String name;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "major")
  private String major;

  @Column(name = "grade")
  private String grade;

  @Column(name = "clazz")
  private String clazz;

  @Column(name = "department")
  private String department;

  @Column(name = "email")
  private String email;

  @Column(name = "pwd")
  private String pwd;

  @Column(name = "role")
  private Integer role;
}
