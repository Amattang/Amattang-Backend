package com.example.amattang.domain.checkList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    @Query("select c from CheckList c where exists (" +
            "select m from QuestionToAnswer m where m.listToQuestion.checkListId.id=c.id and c.user.id=:userId)" +
            "order by c.id desc")
    List<CheckList> findAllIfAnswerExistsByUserId(@Param("userId") String userId);

    boolean existsByIdAndUser_Id(Long id, String userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update CheckList c set c.pinned=:pinned where c.id=:id")
    void updatePinnedByCheckListIdAndPinned(@Param("id") Long id, @Param("pinned") boolean pinned);
}
