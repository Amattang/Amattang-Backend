package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionToAnswer {

    @Id
    @Column(name = "question_to_answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestion;

    @Builder.Default
    @OneToMany(mappedBy = "questionToAnswer",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Answer> answerList = new ArrayList<>();

    public void setAnswerBoolList(List<AnswerBool> answerList) {
        this.answerList = new ArrayList<>();
        answerList.stream()
                .forEach(x -> this.answerList.add(x));
    }

    public void setAnswerStringList(List<AnswerString> answerList) {
        this.answerList = new ArrayList<>();
        answerList.stream()
                .forEach(x -> this.answerList.add(x));
    }

    public QuestionToAnswer(ListToQuestion listToQuestion) {
        this.listToQuestion = listToQuestion;
    }

    public QuestionToAnswer(ListToQuestion listToQuestion, List<Answer> answerList) {
        this.listToQuestion = listToQuestion;
        this.answerList = answerList;
    }
}
