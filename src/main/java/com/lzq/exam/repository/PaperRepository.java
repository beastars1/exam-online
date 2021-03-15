package com.lzq.exam.repository;

import com.lzq.exam.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface PaperRepository extends JpaRepository<Paper, Long> {
}
