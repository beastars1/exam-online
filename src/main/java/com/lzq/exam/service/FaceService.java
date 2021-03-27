package com.lzq.exam.service;

import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.entity.UserFaceInfo;
import com.lzq.exam.repository.UserFaceInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author beastars
 */
@Service
@Slf4j
public class FaceService {
  @Autowired
  private FaceEngineService faceEngineService;

  @Autowired
  private UserFaceInfoRepository userFaceInfoRepository;

  /**
   * 添加 avatarFile 路径下的头像到用户识别库
   *
   * @param avatarFile 头像路径
   * @param studentId  学号
   */
  @Transactional
  public void faceAdd(File avatarFile, Long studentId) {
    if (avatarFile == null)
      throw new ExamException(ExceptionEnum.AVATAR_PATH_IS_NULL);

    ImageInfo imageInfo = ImageFactory.getRGBData(avatarFile);

    // 获取人脸特征
    byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
    if (bytes == null)
      throw new ExamException(ExceptionEnum.NO_FACE_DETECTED);

    UserFaceInfo userFaceInfo = new UserFaceInfo();
    userFaceInfo.setStudentId(studentId);

    // 判断该学生人脸特征是不是已经在数据库了
    Optional<UserFaceInfo> optional = userFaceInfoRepository.findOne(Example.of(userFaceInfo));
    if (optional.isPresent()) {
      UserFaceInfo oldUserInfo = optional.get();
      // 如果已经存在，更新特征值、更新时间，并进行更新
      oldUserInfo.setFaceFeature(bytes);
      oldUserInfo.setUpdateTime(new Date());
      userFaceInfoRepository.saveAndFlush(oldUserInfo);
      return;
    }

    // 如果是新的，就进行新增
    userFaceInfo.setFaceFeature(bytes);
    userFaceInfo.setGroupId(101);
    userFaceInfo.setFaceId(UUID.randomUUID().toString().substring(10));
    userFaceInfo.setCreateTime(new Date());
    userFaceInfo.setUpdateTime(userFaceInfo.getCreateTime());

    // 人脸特征插入到数据库
    userFaceInfoRepository.save(userFaceInfo);
    log.info("[face] add face whose student id : {}", studentId);
  }
}
