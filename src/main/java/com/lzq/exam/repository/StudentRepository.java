package com.lzq.exam.repository;

import com.lzq.exam.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
  /**
   * 根据用户名和密码查询学生
   */
  Student findAdminByNameAndPwd(String name, String pwd);
}
