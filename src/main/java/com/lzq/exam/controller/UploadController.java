package com.lzq.exam.controller;

import com.lzq.exam.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
  @Autowired
  private UploadService uploadService;

  /**
   * 上传用户头像
   *
   * @param file 头像文件
   * @param id   用户的id
   * @return 保存的头像地址
   */
  @PostMapping
  public ResponseEntity<String> uploadAvatar(
    @RequestBody MultipartFile file,
    @RequestParam("id") Long id,
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(uploadService.uploadAvatar(file, id, request));
  }
}
