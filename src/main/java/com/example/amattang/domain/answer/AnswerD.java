package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer_type_d")
public class AnswerD extends Answer{
    @Id
    @Column(name = "answer_answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestionId;

    @OneToMany(mappedBy = "answerId")
    List<AnswerDAnswer> answerList = new ArrayList<>();

    public AnswerD(Long id, ListToQuestion listToQuestionId, List<AnswerDAnswer> answerList) {
        super(id, listToQuestionId);
        this.answerList = answerList;
    }
}
