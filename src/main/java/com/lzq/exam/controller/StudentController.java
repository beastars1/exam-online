package com.lzq.exam.controller;

import com.lzq.exam.vo.PageResult;
import com.lzq.exam.entity.Student;
import com.lzq.exam.service.StudentService;
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
@RequestMapping("/student")
public class StudentController {
  @Autowired
  private StudentService studentService;

  /**
   * 分页查询学生信息
   *
   * @param page 第几页，从0开始
   * @param size 每页的大小
   * @return 自定义分页视图模型
   */
  @GetMapping("/page")
  public ResponseEntity<PageResult<Student>> findStudentByPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {
    return ResponseEntity.ok(studentService.findStudentByPage(page, size));
  }

  /**
   * 根据学号查询学生
   */
  @GetMapping("/{studentId}")
  public ResponseEntity<Student> findStudentById(@PathVariable Long studentId) {
    return ResponseEntity.ok(studentService.findStudentById(studentId));
  }

  /**
   * 新增学生
   */
  @PostMapping
  public ResponseEntity<Void> saveStudent(@RequestBody Student student) {
    studentService.saveStudent(student);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据 id 删除学生
   */
  @DeleteMapping("/{studentId}")
  public ResponseEntity<Void> deleteStudentById(@PathVariable Long studentId) {
    studentService.deleteStudentById(studentId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 更新学生信息
   */
  @PutMapping
  public ResponseEntity<Void> updateStudent(@RequestBody Student student) {
    studentService.updateStudent(student);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 更新学生密码
   */
  public ResponseEntity<Void> updateStudentPwd(@RequestBody Student student) {
    studentService.updateStudentPwd(student);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
