package com.example.amattang.domain.commonQuestion;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommonQuestionRepository extends JpaRepository<CommonQuestion, Long> {

    List<CommonQuestion> findByMainCategory(String category, Sort sort);
}
