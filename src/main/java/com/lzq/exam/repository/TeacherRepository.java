package com.lzq.exam.repository;

import com.lzq.exam.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
  /**
   * 根据工号和密码查询教师
   */
  Teacher findByIdAndPwd(Long id, String pwd);
}
