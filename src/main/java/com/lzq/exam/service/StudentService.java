package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.vo.PageResult;
import com.lzq.exam.entity.Student;
import com.lzq.exam.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author beastars
 */
@Service
@Slf4j
public class StudentService {
  @Autowired
  private StudentRepository studentRepository;

  /**
   * 分页查询学生信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  public PageResult<Student> findStudentByPage(Integer page, Integer size) {
    Page<Student> pages = studentRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    List<Student> students = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageNumber = pages.getNumber();
    log.info("[student] query students by page : {} ,size : {}", page + 1, size);
    return new PageResult<>(totalElements, students, pageNumber, size);
  }

  /**
   * 根据学号查询学生
   */
  public Student findStudentById(Long studentId) {
    Optional<Student> optional = studentRepository.findById(studentId);
    log.info("[student] query a student whose id : {}", studentId);
    return optional.orElseThrow(() -> new ExamException(ExceptionEnum.STUDENT_NOT_FIND));
  }

  /**
   * 新增学生
   */
  @Transactional
  public void saveStudent(Student student) {
    log.info("[student] save a new student whose id : {}", student.getId());
    studentRepository.save(student);
  }

  /**
   * 根据 id 删除学生
   */
  @Transactional
  public void deleteStudentById(Long studentId) {
    log.info("[student] delete a student whose id : {}", studentId);
    studentRepository.deleteById(studentId);
  }

  /**
   * 更新学生信息
   */
  @Transactional
  public void updateStudent(Student student) {
    log.info("[student] update a student whose id : {}", student.getId());
    studentRepository.saveAndFlush(student);
  }

  /**
   * 更新学生密码
   */
  @Transactional
  public void updateStudentPwd(String pwd, Long studentId) {
    log.info("[student] update a student password whose id : {}", studentId);
    Student student = findStudentById(studentId);
    student.setPwd(pwd);
    studentRepository.saveAndFlush(student);
  }
}
