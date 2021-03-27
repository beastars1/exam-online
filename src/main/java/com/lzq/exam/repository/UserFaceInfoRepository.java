package com.lzq.exam.repository;

import com.lzq.exam.entity.UserFaceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author beastars
 */
public interface UserFaceInfoRepository extends JpaRepository<UserFaceInfo, Integer> {
  List<UserFaceInfo> getUserFaceInfoByGroupId(Integer groupId);
}
