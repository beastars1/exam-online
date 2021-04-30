package com.lzq.exam.service;

import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.common.PrefixEnum;
import com.lzq.exam.entity.UserFaceInfo;
import com.lzq.exam.repository.UserFaceInfoRepository;
import com.lzq.exam.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

  @Autowired
  private RedisUtils redisUtils;

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
      log.info("[face] update face feature whose student id : {}", studentId);
      userFaceInfoRepository.saveAndFlush(oldUserInfo);
      return;
    }

    // 如果是新的，就进行新增
    userFaceInfo.setFaceFeature(bytes);
    String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    userFaceInfo.setFaceId(uuid);
    userFaceInfo.setCreateTime(new Date());
    userFaceInfo.setUpdateTime(userFaceInfo.getCreateTime());

    // 人脸特征插入到数据库
    userFaceInfoRepository.save(userFaceInfo);
    log.info("[face] add face feature whose student id : {}", studentId);
  }

  /**
   * 人脸识别，检测是否和照片是同一个人
   *
   * @param file 要识别的图片的base64码
   * @return 将这次判断的特征值返回过去，这样下次同一个人比较就不需要多次查询数据库了
   */
  public boolean faceSearchById(String file, Long studentId) {
    byte[] studentFeature = null;
    byte[] fileFeature = null;
    // 如果缓存中不存在该学生的特征值
    if (!redisUtils.hasKey(PrefixEnum.EXAM_FACE.getPrefix() + studentId)) {
      // 从数据库中提取
      studentFeature = userFaceInfoRepository.findByStudentId(studentId).getFaceFeature();
      if (studentFeature == null) {
        log.error("[face] cannot find face feature whose student id : {}", studentId);
        return false;
      }
      // 存入缓存，缓存4个小时
      // 存入redis的是特征值的字符串转换，默认使用的utf-8是可变长编码，由string转为byte[]时会改变长度，因此使用iso-8859-1编码
      if (!redisUtils.set(
        PrefixEnum.EXAM_FACE.getPrefix() + studentId,
        new String(studentFeature, StandardCharsets.ISO_8859_1),
        60 * 60 * 4
      )) {
        log.error("[redis] store face feature failed whose student id : {}", studentId);
      }
    } else {
      // 直接从缓存中获取特征值
      studentFeature = redisUtils.get(PrefixEnum.EXAM_FACE.getPrefix() + studentId)
        .toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    try {
      // 获取图片的特征值
      fileFeature = getFeatureFromImage(file);
      // 比较人脸特征
      return faceEngineService.compareFaceFeature(fileFeature, studentFeature);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将文件解码为byte数组
   */
  private byte[] getFeatureFromImage(String file) throws IOException {
    // 使用base64解码图片
    byte[] bytes = Base64Utils.decodeFromString(base64Process(file));
    BufferedImage bufImage = ImageIO.read(new ByteArrayInputStream(bytes));
    // 转化成sdk识别需要的类型
    ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(bufImage);

    // 抽取图片的人脸特征
    byte[] feature = faceEngineService.extractFaceFeature(imageInfo);
    if (feature == null)
      throw new ExamException(ExceptionEnum.NO_FACE_DETECTED);
    return feature;
  }

  /**
   * 将canvas.toDataURL()获取传入的data-URL格式化，去掉前面的“data:image/png;base64,”
   * e.g:
   * "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAADElEQVQImWNgoBMAAABpAAFEI8ARAAAAAElFTkSuQmCC"
   */
  private String base64Process(String base64Str) {
    if (!StringUtils.isEmpty(base64Str)) {
      String photoBase64 = base64Str.substring(0, 30).toLowerCase();
      int indexOf = photoBase64.indexOf("base64,");
      if (indexOf > 0) {
        base64Str = base64Str.substring(indexOf + 7);
      }
      return base64Str;
    } else {
      throw new ExamException(ExceptionEnum.BASE64_ERROR);
    }
  }
}
