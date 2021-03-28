package com.lzq.exam.service;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.toolkit.ImageInfo;
import com.lzq.exam.common.ExamException;
import com.lzq.exam.common.ExceptionEnum;
import com.lzq.exam.common.FaceEngineFactory;
import com.lzq.exam.entity.UserFaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FaceEngineService {

  @Value("${config.arcface-sdk.sdk-lib-path}")
  public String sdkLibPath;
  @Value("${config.arcface-sdk.app-id}")
  public String appId;
  @Value("${config.arcface-sdk.sdk-key}")
  public String sdkKey;
  @Value("${config.arcface-sdk.thread-pool-size}")
  public Integer threadPoolSize;
  /* 人脸相似预期值，认为只要相似度大于 passRate 就是同一个人 */
  @Value("${config.arcface-sdk.pass-rate}")
  private Integer passRate;

  private GenericObjectPool<FaceEngine> faceEngineObjectPool;

  @PostConstruct
  public void init() {
    GenericObjectPoolConfig<FaceEngine> poolConfig = new GenericObjectPoolConfig<>();
    poolConfig.setMaxIdle(threadPoolSize);
    poolConfig.setMaxTotal(threadPoolSize);
    poolConfig.setMinIdle(threadPoolSize);
    poolConfig.setLifo(false);

    //引擎配置
    EngineConfiguration engineConfiguration = new EngineConfiguration();
    engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
    engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);

    //功能配置
    FunctionConfiguration functionConfiguration = new FunctionConfiguration();
    functionConfiguration.setSupportAge(true);
    functionConfiguration.setSupportFace3dAngle(true);
    functionConfiguration.setSupportFaceDetect(true);
    functionConfiguration.setSupportFaceRecognition(true);
    functionConfiguration.setSupportGender(true);
    functionConfiguration.setSupportLiveness(true);
    functionConfiguration.setSupportIRLiveness(true);
    engineConfiguration.setFunctionConfiguration(functionConfiguration);

    //底层库算法对象池
    faceEngineObjectPool = new GenericObjectPool<>(
      new FaceEngineFactory(sdkLibPath, appId, sdkKey, engineConfiguration),
      poolConfig
    );
  }

  /**
   * 抽取人脸特征
   *
   * @param imageInfo 要提取的图像
   * @return 返回人脸特征的二进制表示
   */
  public byte[] extractFaceFeature(ImageInfo imageInfo) {
    FaceEngine faceEngine = null;
    try {
      //获取引擎对象
      faceEngine = faceEngineObjectPool.borrowObject();

      //人脸检测得到人脸列表
      List<FaceInfo> faceInfoList = new ArrayList<>();

      //人脸检测
      int code = faceEngine.detectFaces(
        imageInfo.getImageData(),
        imageInfo.getWidth(),
        imageInfo.getHeight(),
        imageInfo.getImageFormat(),
        faceInfoList // 检测到的人脸信息会输出到该列表中
      );

      // 返回值为0表示成功
      if (code == 0) {
        FaceFeature faceFeature = new FaceFeature();
        //提取人脸特征
        faceEngine.extractFaceFeature(
          imageInfo.getImageData(),
          imageInfo.getWidth(),
          imageInfo.getHeight(),
          imageInfo.getImageFormat(),
          faceInfoList.get(0),
          faceFeature // 提取到的人脸特征信息会输出到该对象
        );

        return faceFeature.getFeatureData();
      }
    } catch (Exception e) {
      log.error("", e);
    } finally {
      if (faceEngine != null) {
        //释放引擎对象
        faceEngineObjectPool.returnObject(faceEngine);
      }
    }

    log.info("[face] cannot detect face");
    return null;
  }

  /**
   * 比对人脸特征
   *
   * @param faceFeature     传入的特征值
   * @param studentFaceInfo 要比对的学生的人脸信息
   * @return 是否是同一个人
   */
  public boolean compareFaceFeature(byte[] faceFeature, UserFaceInfo studentFaceInfo) {
    // 设置要比较的目标特征，即摄像头抓取到的图像
    FaceFeature targetFaceFeature = new FaceFeature();
    targetFaceFeature.setFeatureData(faceFeature);
    // 获取人脸引擎
    FaceEngine faceEngine;
    try {
      faceEngine = faceEngineObjectPool.borrowObject();
    } catch (Exception e) {
      log.error("[face] unknown exception : {}", e.getMessage());
      throw new ExamException(ExceptionEnum.UNKNOWN_EXCEPTION);
    }
    // 设置要比较的源特征，即该学生在数据库的人脸特征
    FaceFeature sourceFaceFeature = new FaceFeature();
    sourceFaceFeature.setFeatureData(studentFaceInfo.getFaceFeature());
    // 比对相似度
    FaceSimilar faceSimilar = new FaceSimilar();
    faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
    // 获取相似值
    int similarValue = plusHundred(faceSimilar.getScore());
    log.info("[face] the image's similar value : {}", similarValue);
    // 相似值大于配置预期，就认为是同一个人
    return similarValue > passRate;
  }

  /**
   * 格式化float的数值
   */
  private int plusHundred(Float value) {
    BigDecimal target = new BigDecimal(value);
    BigDecimal hundred = new BigDecimal("100");
    return target.multiply(hundred).intValue();
  }
}
