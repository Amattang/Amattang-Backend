package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.answer.Answer;
import com.example.amattang.domain.answer.AnswerBool;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeA;
import com.example.amattang.domain.commonQuestion.QuestionTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class AnswerADto {

    List<Answer> ans;

    @Getter
    @AllArgsConstructor
    static class Answer {

        @ApiModelProperty(name = "답변 지시문", example = "예")
        private String type;

        @ApiModelProperty(name = "답변", example = "true")
        private boolean val;

        @ApiModelProperty(name = "어떤 경우 빨간색으로 표시하면 되는가?", example = "true")
        private boolean redType;
    }

    public static List<Answer> fromQuestion(CommonQuestionTypeA question) {

        List<Answer> list = new ArrayList<>();

        for (QuestionTemplate q : question.getTemplates()) {
            list.add(new Answer(q.getTemplate(), false, q.isRedType()));
        }

//        Answer answerTrue = new Answer("예", false, (question.getRedType().equals(REDTYPE.TRUE))? true : false);
//        Answer answerFalse = new Answer("아니오", false, (question.getRedType().equals(REDTYPE.FALSE))? true : false);
//        ArrayList arrayList = new ArrayList(Arrays.asList(answerTrue, answerFalse));
//        return AnswerADto.builder()
//                .ans(list)
//                .build();
        return list;
    }

    public static List<Answer> fromAnswer(CommonQuestionTypeA question, List<com.example.amattang.domain.answer.Answer> answer, Long id) {
        Collections.sort(answer, (a,b) -> a.getId().compareTo(b.getId()));
        List<Answer> list = new ArrayList<>();

        for (com.example.amattang.domain.answer.Answer a : answer) {
            list.add(new Answer(a.getType(), ((AnswerBool) a).isAns(), a.isRedType()));
        }

//        return AnswerADto.builder()
//                .id(id)
//                .ans(list)
//                .build();
        return list;
    }



}
