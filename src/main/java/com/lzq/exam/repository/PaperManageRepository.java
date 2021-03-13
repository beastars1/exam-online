package com.lzq.exam.repository;

import com.lzq.exam.entity.PaperManage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface PaperManageRepository extends JpaRepository<PaperManage, Long> {
}
