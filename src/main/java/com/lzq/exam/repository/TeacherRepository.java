package com.lzq.exam.repository;

import com.lzq.exam.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
