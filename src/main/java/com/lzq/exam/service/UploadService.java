package com.lzq.exam.service;

import com.lzq.exam.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author beastars
 */
@Service
@Slf4j
public class UploadService {
  @Autowired
  private StudentService studentService;

  /**
   * 上传用户头像
   *
   * @param file 头像文件
   * @param id   用户的id
   */
  @Transactional
  public String uploadAvatar(MultipartFile file, Long id, HttpServletRequest request) {
    try {
      String path = ResourceUtils.getURL("classpath:").getPath() + "static/avatar/";
      System.out.println(path);
      // 保存到数据库的头像地址
      String url = request.getContextPath() + "/api/avatar/";
      File filePath = new File(path);
      log.info("[upload] 文件的保存路径 : {}", path);
      if (!filePath.exists() && !filePath.isDirectory()) {
        log.info("[upload] 目录不存在，创建目录 : {}", filePath);
        filePath.mkdirs(); // mkdir()不会创建目录,找不到相应路径时返回false;而mkdirs()当目录不存在时则会创建相应目录
      }
      //获取原始文件名称(包含格式)
      String originalFileName = file.getOriginalFilename();
      //获取文件类型，以最后一个`.`为标识
      String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
      String fileName  = UUID.randomUUID().toString() + "." + type;
      //在指定路径下创建一个文件
      File targetFile = new File(path, fileName);
      file.transferTo(targetFile);
      log.info("[upload] student id : {} avatar name : {} in : {}", id, fileName, targetFile.getPath());

      if (targetFile.exists()) {
        Student student = studentService.findStudentById(id);
        student.setAvatar(url + fileName);
        log.info("[upload] db url : {}", url + fileName);
      }

      return url + fileName;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
