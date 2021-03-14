package com.lzq.exam.controller;

import com.lzq.exam.entity.Teacher;
import com.lzq.exam.service.TeacherService;
import com.lzq.exam.vo.PageResult;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
  @Autowired
  private TeacherService teacherService;

  /**
   * 分页查询老师信息
   */
  @GetMapping("/page")
  public ResponseEntity<PageResult<Teacher>> findTeacherByPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {
    return ResponseEntity.ok(teacherService.findTeacherByPage(page, size));
  }

  /**
   * 查询所有教师
   */
//  @GetMapping
//  public ResponseEntity<List<Teacher>> findTeacherById() {
//    return ResponseEntity.ok(teacherService.findAllTeacher());
//  }

  /**
   * 根据工号查询教师
   */
  @GetMapping("/{teacherId}")
  public ResponseEntity<Teacher> findTeacherById(@PathVariable Long teacherId) {
    return ResponseEntity.ok(teacherService.findTeacherById(teacherId));
  }

  /**
   * 新增教师
   */
  @PostMapping
  public ResponseEntity<Void> saveTeacher(@RequestBody Teacher teacher) {
    teacherService.saveTeacher(teacher);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据 id 删除教师信息
   */
  @DeleteMapping("/{teacherId}")
  public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) {
    teacherService.deleteTeacher(teacherId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 更新教师信息
   */
  @PutMapping
  public ResponseEntity<Void> updateTeacher(@RequestBody Teacher teacher) {
    teacherService.updateTeacher(teacher);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
