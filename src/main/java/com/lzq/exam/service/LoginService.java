package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
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

  public Object login(String username, String pwd) {
    Object obj;
    if ((obj = adminRepository.findAdminByNameAndPwd(username, pwd)) != null) {
      log.info("a administer has logged in");
      return obj;
    }
    else if ((obj = teacherRepository.findAdminByNameAndPwd(username, pwd)) != null) {
      log.info("a teacher has logged in");
      return obj;
    } else if ((obj = studentRepository.findAdminByNameAndPwd(username, pwd)) != null) {
      log.info("a student has logged in");
      return obj;
    }
    throw new ExamException(ExceptionEnum.USER_NOT_FIND);
  }
}
