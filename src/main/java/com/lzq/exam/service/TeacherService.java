package com.lzq.exam.service;

import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.entity.Teacher;
import com.lzq.exam.repository.TeacherRepository;
import com.lzq.exam.vo.PageResult;
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
public class TeacherService {
  @Autowired
  private TeacherRepository teacherRepository;

  /**
   * 分页查询教师信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  public PageResult<Teacher> findTeacherByPage(Integer page, Integer size) {
    Page<Teacher> pages = teacherRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    List<Teacher> teachers = pages.getContent();
    long totalElements = pages.getTotalElements();
    int pageNumber = pages.getNumber();
    log.info("[Teacher] query teachers by page : {} ,size : {}", page + 1, size);
    return new PageResult<>(totalElements, teachers, pageNumber, size);
  }

  /**
   * 查询所有教师
   */
//  public List<Teacher> findAllTeacher() {
//    log.info("[teacher] query all teachers");
//    return teacherRepository.findAll();
//  }

  /**
   * 根据工号查询教师
   */
  public Teacher findTeacherById(Long teacherId) {
    Optional<Teacher> optional = teacherRepository.findById(teacherId);
    log.info("[teacher] query a teacher whose id : {}", teacherId);
    return optional.orElseThrow(() -> new ExamException(ExceptionEnum.TEACHER_NOT_FIND));
  }

  /**
   * 新增教师
   */
  @Transactional
  public void saveTeacher(Teacher teacher) {
    log.info("[teacher] save a new teacher whose id : {}", teacher.getId());
    teacherRepository.save(teacher);
  }

  /**
   * 根据 id 删除教师信息
   */
  @Transactional
  public void deleteTeacher(Long teacherId) {
    log.info("[teacher] delete a teacher whose id : {}", teacherId);
    teacherRepository.deleteById(teacherId);
  }

  /**
   * 更新教师信息
   */
  @Transactional
  public void updateTeacher(Teacher teacher) {
    log.info("[teacher] update a teacher whose id : {}", teacher.getId());
    teacherRepository.saveAndFlush(teacher);
  }
}
