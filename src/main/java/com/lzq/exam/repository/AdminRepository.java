package com.lzq.exam.repository;

import com.lzq.exam.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
  /**
   * 根据用户名和密码查询管理员
   */
  Admin findAdminByNameAndPwd(String name, String pwd);
}
