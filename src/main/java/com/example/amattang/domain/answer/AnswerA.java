package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer_type_a")
public class AnswerA extends Answer {
    @Id
    @Column(name = "answer_answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestionId;

    @Column(name = "ans_true")
    private Boolean ansTrue;

    @Column(name = "ans_false")
    private Boolean ansFalse;

    @Column(name = "red_type")
    private Boolean redType;

    public AnswerA(Long id, ListToQuestion listToQuestionId, Boolean ansTrue, Boolean ansFalse, Boolean redType) {
        super(id, listToQuestionId);
        this.ansTrue = ansTrue;
        this.ansFalse = ansFalse;
        this.redType = redType;
    }
}
