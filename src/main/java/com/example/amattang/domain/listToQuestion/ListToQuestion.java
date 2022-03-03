package com.example.amattang.domain.listToQuestion;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.commonQuestion.CommonQuestion;

import javax.persistence.*;

@Entity
@Table(name = "check_list_common_question")
public class ListToQuestion {

    @Id
    @Column(name = "check_list_common_question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "check_list_check_list_id")
    private CheckList checkListId;

    @ManyToOne
    @JoinColumn(name = "common_question_question_id")
    private CommonQuestion commonQuestionId;

    private Boolean visibility;
}
