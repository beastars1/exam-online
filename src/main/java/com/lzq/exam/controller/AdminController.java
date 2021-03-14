package com.lzq.exam.controller;

import com.lzq.exam.entity.Admin;
import com.lzq.exam.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
//  static final Logger log = LoggerFactory.getLogger(AdminController.class);
  @Autowired
  private AdminService adminService;

  /**
   * 查询所有的管理员信息，并模糊掉密码
   */
  @GetMapping
  public ResponseEntity<List<Admin>> findAllAdmin() {
    List<Admin> admins = adminService.findAllAdmin();
    admins.forEach(a -> a.setPwd(""));
    return ResponseEntity.ok(admins);
  }

  /**
   * 根据 id 查询管理员信息
   */
  @GetMapping("/{adminId}")
  public ResponseEntity<Admin> findAdminById(@PathVariable Long adminId) {
    Admin admin = adminService.findAdminById(adminId);
    admin.setPwd("");
    return ResponseEntity.ok(admin);
  }

  /**
   * 新增管理员
   */
  @PostMapping
  public ResponseEntity<Void> saveAdmin(@RequestBody Admin admin) {
    adminService.saveAdmin(admin);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据 id 删除管理员
   */
  @DeleteMapping("/{adminId}")
  public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
    adminService.deleteAdminById(adminId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 更新管理员信息
   */
  @PutMapping("/{adminId}")
  public ResponseEntity<Void> updateAdmin(@PathVariable Long adminId, @RequestBody Admin admin) {
    admin.setId(adminId);
    adminService.updateAdmin(admin);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
