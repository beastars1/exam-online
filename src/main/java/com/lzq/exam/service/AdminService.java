package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.entity.Admin;
import com.lzq.exam.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

/**
 * @author beastars
 */
@Service
public class AdminService {
  @Autowired
  private AdminRepository adminRepository;

  /**
   * 查询所有的管理员信息
   */
  public List<Admin> findAllAdmin() {
    List<Admin> admins = adminRepository.findAll();
    if (admins.size() == 0) {
      throw new ExamException(ExceptionEnum.ADMIN_NOT_FIND);
    }
    return admins;
  }

  /**
   * 根据 id 查询管理员信息
   */
  public Admin findAdminById(Long adminId) {
    Optional<Admin> optional = adminRepository.findById(adminId);
    return optional.orElseThrow(() -> new ExamException(ExceptionEnum.ADMIN_NOT_FIND));
  }

  /**
   * 新增管理员
   */
  @Transactional
  public void saveAdmin(Admin admin) {
    adminRepository.save(admin);
  }

  /**
   * 根据 id 删除管理员
   */
  @Transactional
  public void deleteAdminById(@PathVariable Long adminId) {
    adminRepository.deleteById(adminId);
  }

  /**
   * 更新管理员信息
   */
  @Transactional
  public void updateAdmin(Admin admin) {
    adminRepository.saveAndFlush(admin);
  }
}
