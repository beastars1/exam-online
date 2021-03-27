package com.lzq.exam.service;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.toolkit.ImageInfo;
import com.lzq.exam.common.FaceEngineFactory;
import com.lzq.exam.repository.UserFaceInfoRepository;
import com.lzq.exam.vo.FaceUserInfo;
import com.lzq.exam.vo.ProcessInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

  private Integer passRate = 80;

  private ExecutorService executorService;

  @Autowired
  private UserFaceInfoRepository userFaceInfoRepository;

  private GenericObjectPool<FaceEngine> faceEngineObjectPool;

  @PostConstruct
  public void init() {
    executorService = Executors.newFixedThreadPool(threadPoolSize);
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


  private int plusHundred(Float value) {
    BigDecimal target = new BigDecimal(value);
    BigDecimal hundred = new BigDecimal("100");
    return target.multiply(hundred).intValue();

  }

  public List<FaceInfo> detectFaces(ImageInfo imageInfo) {
    FaceEngine faceEngine = null;
    try {
      //获取引擎对象
      faceEngine = faceEngineObjectPool.borrowObject();

      //人脸检测得到人脸列表
      List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();

      //人脸检测
      faceEngine.detectFaces(
        imageInfo.getImageData(),
        imageInfo.getWidth(),
        imageInfo.getHeight(),
        imageInfo.getImageFormat(),
        faceInfoList
      );
      return faceInfoList;
    } catch (Exception e) {
      log.error("", e);
    } finally {
      if (faceEngine != null) {
        //释放引擎对象
        faceEngineObjectPool.returnObject(faceEngine);
      }
    }
    return null;
  }

  public List<ProcessInfo> process(ImageInfo imageInfo) {
    FaceEngine faceEngine = null;
    try {
      //获取引擎对象
      faceEngine = faceEngineObjectPool.borrowObject();
      //人脸检测得到人脸列表
      List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
      //人脸检测
      faceEngine.detectFaces(
        imageInfo.getImageData(),
        imageInfo.getWidth(),
        imageInfo.getHeight(),
        imageInfo.getImageFormat(),
        faceInfoList
      );

      int processResult = faceEngine.process(
        imageInfo.getImageData(),
        imageInfo.getWidth(),
        imageInfo.getHeight(),
        imageInfo.getImageFormat(),
        faceInfoList,
        FunctionConfiguration.builder().supportAge(true).supportGender(true).build()
      );
      List<ProcessInfo> processInfoList = new LinkedList<>();

      List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
      //性别提取
      int genderCode = faceEngine.getGender(genderInfoList);
      //年龄提取
      List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
      int ageCode = faceEngine.getAge(ageInfoList);
      for (int i = 0; i < genderInfoList.size(); i++) {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setGender(genderInfoList.get(i).getGender());
        processInfo.setAge(ageInfoList.get(i).getAge());
        processInfoList.add(processInfo);
      }
      return processInfoList;

    } catch (Exception e) {
      log.error("", e);
    } finally {
      if (faceEngine != null) {
        //释放引擎对象
        faceEngineObjectPool.returnObject(faceEngine);
      }
    }

    return null;
  }

  /**
   * 人脸特征
   *
   * @param imageInfo
   * @return
   */
  public byte[] extractFaceFeature(ImageInfo imageInfo) {

    FaceEngine faceEngine = null;
    try {
      //获取引擎对象
      faceEngine = faceEngineObjectPool.borrowObject();

      //人脸检测得到人脸列表
      List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();

      //人脸检测
      int i = faceEngine.detectFaces(
        imageInfo.getImageData(),
        imageInfo.getWidth(),
        imageInfo.getHeight(),
        imageInfo.getImageFormat(),
        faceInfoList
      );

      if (!CollectionUtils.isEmpty(faceInfoList)) {
        FaceFeature faceFeature = new FaceFeature();
        //提取人脸特征
        faceEngine.extractFaceFeature(
          imageInfo.getImageData(),
          imageInfo.getWidth(),
          imageInfo.getHeight(),
          imageInfo.getImageFormat(),
          faceInfoList.get(0),
          faceFeature
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

    return null;
  }

  public List<FaceUserInfo> compareFaceFeature(byte[] faceFeature, Integer groupId) throws InterruptedException, ExecutionException {
    List<FaceUserInfo> resultFaceInfoList = new LinkedList<>();//识别到的人脸列表
    FaceFeature targetFaceFeature = new FaceFeature();
    targetFaceFeature.setFeatureData(faceFeature);
    List<FaceUserInfo> faceInfoList = new ArrayList<>();
    //从数据库中取出人脸库
    userFaceInfoRepository.getUserFaceInfoByGroupId(groupId)
      .forEach(userFaceInfo -> {
        faceInfoList.add(
          new FaceUserInfo(
            userFaceInfo.getFaceId(),
            userFaceInfo.getStudentId(),
            null,
            userFaceInfo.getFaceFeature()
          )
        );
      });
    //分成1000一组，多线程处理
    List<List<FaceUserInfo>> faceUserInfoPartList = new ArrayList<>();
    int size = faceInfoList.size();
    int count = size / 1000;
    if (size % 1000 > 0)
      count += 1;
    for (int i = 0; i < count; i++) {
      if (i == count - 1) {
        faceUserInfoPartList.add(faceInfoList.subList(i * 1000, size));
      } else {
        faceUserInfoPartList.add(faceInfoList.subList(i * 1000, 1000 * (i + 1)));
      }
    }
    CompletionService<List<FaceUserInfo>> completionService = new ExecutorCompletionService<>(executorService);
    for (List<FaceUserInfo> part : faceUserInfoPartList) {
      completionService.submit(new CompareFaceTask(part, targetFaceFeature));
    }
    for (int i = 0; i < faceUserInfoPartList.size(); i++) {
      List<FaceUserInfo> faceUserInfoList = completionService.take().get();
      if (!CollectionUtils.isEmpty(faceInfoList)) {
        resultFaceInfoList.addAll(faceUserInfoList);
      }
    }

    //从大到小排序
    resultFaceInfoList.sort(
      (h1, h2) -> h2.getSimilarValue().compareTo(h1.getSimilarValue())
    );
    return resultFaceInfoList;
  }

  private class CompareFaceTask implements Callable<List<FaceUserInfo>> {

    private List<FaceUserInfo> faceUserInfoList;
    private FaceFeature targetFaceFeature;

    public CompareFaceTask(List<FaceUserInfo> faceUserInfoList, FaceFeature targetFaceFeature) {
      this.faceUserInfoList = faceUserInfoList;
      this.targetFaceFeature = targetFaceFeature;
    }

    @Override
    public List<FaceUserInfo> call() throws Exception {
      FaceEngine faceEngine = null;
      List<FaceUserInfo> resultFaceInfoList = new LinkedList<>();//识别到的人脸列表
      try {
        faceEngine = faceEngineObjectPool.borrowObject();
        for (FaceUserInfo faceUserInfo : faceUserInfoList) {
          FaceFeature sourceFaceFeature = new FaceFeature();
          sourceFaceFeature.setFeatureData(faceUserInfo.getFaceFeature());
          FaceSimilar faceSimilar = new FaceSimilar();
          faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
          Integer similarValue = plusHundred(faceSimilar.getScore());//获取相似值
          if (similarValue > passRate) {//相似值大于配置预期，加入到识别到人脸的列表

            FaceUserInfo info = new FaceUserInfo();
            info.setStudentId(faceUserInfo.getStudentId());
            info.setFaceId(faceUserInfo.getFaceId());
            info.setSimilarValue(similarValue);
            resultFaceInfoList.add(info);
          }
        }
      } catch (Exception e) {
        log.error("", e);
      } finally {
        if (faceEngine != null) {
          faceEngineObjectPool.returnObject(faceEngine);
        }
      }

      return resultFaceInfoList;
    }
  }
}
