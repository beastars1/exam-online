package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.common.Role;
import com.lzq.exam.repository.AdminRepository;
import com.lzq.exam.repository.StudentRepository;
import com.lzq.exam.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author beastars
 */
@Service
@Slf4j
public class LoginService {
  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private StudentRepository studentRepository;

  public Role login(String username, String pwd) {
    Role role;
    if ((role = adminRepository.findAdminByNameAndPwd(username, pwd)) != null) {
      log.info("a administer has logged in");
      return role;
    }
    else if ((role = teacherRepository.findAdminByNameAndPwd(username, pwd)) != null) {
      log.info("a teacher has logged in");
      return role;
    } else if ((role = studentRepository.findAdminByNameAndPwd(username, pwd)) != null) {
      log.info("a student has logged in");
      return role;
    }
    throw new ExamException(ExceptionEnum.USER_NOT_FIND);
  }
}
