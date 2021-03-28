package com.lzq.exam.repository;

import com.lzq.exam.entity.UserFaceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface UserFaceInfoRepository extends JpaRepository<UserFaceInfo, Integer> {
  UserFaceInfo findByStudentId(Long studentId);
}
