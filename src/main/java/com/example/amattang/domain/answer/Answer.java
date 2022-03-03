package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Answer {

    @Id
    @JoinColumn(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestionId;

}
