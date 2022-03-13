package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.answer.AnswerA;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeA;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class AnswerADto {

    @ApiModelProperty(value = "답변 아이디", example = "1")
    private Long answerId;

    List<Answer> ans;

    @Getter
    @AllArgsConstructor
    static class Answer {

        @ApiModelProperty(name = "답변 지시문", example = "예")
        private String type;

        @ApiModelProperty(name = "답변", example = "true")
        private boolean ans;

        @ApiModelProperty(name = "어떤 경우 빨간색으로 표시하면 되는가?", example = "true")
        private boolean redType;
    }

    public static AnswerADto fromQuestion(CommonQuestionTypeA question) {
        Answer answerTrue = new Answer("예", false, (question.getRedType().equals(REDTYPE.TRUE))? true : false);
        Answer answerFalse = new Answer("아니오", false, (question.getRedType().equals(REDTYPE.FALSE))? true : false);
        ArrayList arrayList = new ArrayList(Arrays.asList(answerTrue, answerFalse));
        return AnswerADto.builder()
                .ans(arrayList)
                .build();
    }

    public static AnswerADto fromAnswer(CommonQuestionTypeA question, AnswerA answer, Long id) {
        Answer answerTrue = new Answer("예", answer.getAnsTrue(), (question.getRedType().equals(REDTYPE.TRUE))? true : false);
        Answer answerFalse = new Answer("아니오", answer.getAnsFalse(), (question.getRedType().equals(REDTYPE.FALSE))? true : false);
        ArrayList arrayList = new ArrayList(Arrays.asList(answerTrue, answerFalse));
        return AnswerADto.builder()
                .answerId(id)
                .ans(arrayList)
                .build();
    }



}
