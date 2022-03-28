package com.example.amattang.domain.listToQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListToQuestionRepository extends JpaRepository<ListToQuestion, Long> {

    Optional<ListToQuestion> findByCheckListId_IdAndCommonQuestionId_Id(Long checkList, Long question);

    @Query("select l from ListToQuestion l where l.checkListId.id=:id and l.commonQuestionId.mainCategory='기본정보' and l.commonQuestionId.basicType is not null")
    List<ListToQuestion> findCheckListMainInformation(@Param("id") Long id);
}
