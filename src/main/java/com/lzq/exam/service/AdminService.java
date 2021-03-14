package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.entity.Admin;
import com.lzq.exam.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AdminService {
  @Autowired
  private AdminRepository adminRepository;

  /**
   * 查询所有的管理员信息
   */
  public List<Admin> findAllAdmin() {
    log.info("[admin] query all admins");
    return adminRepository.findAll();
  }

  /**
   * 根据 id 查询管理员信息
   */
  public Admin findAdminById(Long adminId) {
    Optional<Admin> optional = adminRepository.findById(adminId);
    log.info("[admin] query a admin whose id : {}", adminId);
    return optional.orElseThrow(() -> new ExamException(ExceptionEnum.ADMIN_NOT_FIND));
  }

  /**
   * 新增管理员
   */
  @Transactional
  public void saveAdmin(Admin admin) {
    log.info("[admin] save a new admin whose id : {}", admin.getId());
    adminRepository.save(admin);
  }

  /**
   * 根据 id 删除管理员
   */
  @Transactional
  public void deleteAdminById(Long adminId) {
    log.info("[admin] delete a admin whose id : {}", adminId);
    adminRepository.deleteById(adminId);
  }

  /**
   * 更新管理员信息
   */
  @Transactional
  public void updateAdmin(Admin admin) {
    log.info("[admin] update a admin whose id : {}", admin.getId());
    adminRepository.saveAndFlush(admin);
  }
}
