package com.example.amattang.domain.checkList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    @Query("select c from CheckList c where exists (" +
            "select m from QuestionToAnswer m where m.listToQuestion.checkListId.id=c.id and c.user.id=:userId)" +
            "order by c.id desc")
    List<CheckList> findAllIfAnswerExistsByUserId(@Param("userId") String userId);
}
