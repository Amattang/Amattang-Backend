package com.example.amattang.domain.listToQuestion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListToQuestionRepository extends JpaRepository<ListToQuestion, Long> {

    Optional<ListToQuestion> findByCheckListId_IdAndCommonQuestionId_Id(Long checkList, Long question);
}
