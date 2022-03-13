package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer_type_c")
public class AnswerC extends Answer {
    @Id
    @Column(name = "answer_answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestionId;


    @Column(name = "ans_type1")
    private Boolean ansType1;
    @Column(name = "ans_type2")
    private Boolean ansType2;
    @Column(name = "ans_type3")
    private Boolean ansType3;

    @Column(name = "red_type")
    private Boolean redType;

    public AnswerC(Long id, ListToQuestion listToQuestionId, Boolean ansType1, Boolean ansType2, Boolean ansType3, Boolean redType) {
        super(id, listToQuestionId);
        this.ansType1 = ansType1;
        this.ansType2 = ansType2;
        this.ansType3 = ansType3;
        this.redType = redType;
    }
}
