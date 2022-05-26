package com.example.amattang.domain.answer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "answer_type_string")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerString extends Answer{

    private String ans;


    public AnswerString(String type, QuestionToAnswer listToQuestionId, String ans) {
        super(type, false, listToQuestionId);
        this.ans = ans;
    }
}
