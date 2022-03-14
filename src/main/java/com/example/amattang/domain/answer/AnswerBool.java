package com.example.amattang.domain.answer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "answer_type_bool")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerBool extends Answer{

    private boolean ans;


    public AnswerBool(String type, boolean redType, QuestionToAnswer listToQuestionId, boolean ans) {
        super(type, redType, listToQuestionId);
        this.ans = ans;
    }
}
