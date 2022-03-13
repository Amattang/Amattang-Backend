package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer_type_b")
public class AnswerB extends Answer {
    @Id
    @Column(name = "answer_answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestionId;

    private String ans_type1;
    private String ans_type2;

    @Column(name = "description_type1")
    private String descriptionType1;
    @Column(name = "description_type2")
    private String descriptionType2;

    public AnswerB(Long id, ListToQuestion listToQuestionId, String ans_type1, String ans_type2, String descriptionType1, String descriptionType2) {
        super(id, listToQuestionId);
        this.ans_type1 = ans_type1;
        this.ans_type2 = ans_type2;
        this.descriptionType1 = descriptionType1;
        this.descriptionType2 = descriptionType2;
    }
}
