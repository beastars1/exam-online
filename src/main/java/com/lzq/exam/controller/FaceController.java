package com.lzq.exam.controller;

import com.lzq.exam.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/face")
public class FaceController {
  @Autowired
  private FaceService faceService;

  /**
   * 人脸识别，检测是否和照片是同一个人
   *
   * @param file 要识别的图片的base64码
   * @return 是否是同一个人
   */
  @PostMapping("/search")
  public ResponseEntity<Boolean> faceSearch(String file, Long studentId) {
    return ResponseEntity.ok(faceService.faceSearchById(file, studentId));
  }
}
