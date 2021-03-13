package com.lzq.exam.repository;

import com.lzq.exam.entity.ChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author beastars
 */
public interface ChoiceQuestionRepository extends JpaRepository<ChoiceQuestion, Long> {
}
