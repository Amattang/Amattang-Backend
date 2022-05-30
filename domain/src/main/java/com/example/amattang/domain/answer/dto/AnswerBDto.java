package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.answer.AnswerString;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeB;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AnswerBDto {

    @ApiModelProperty(value = "답변 아이디", example = "4")
    private Long id;

    private List<Answer> ans;

    @Getter
    @AllArgsConstructor
    static class Answer {
        @ApiModelProperty(name = "입력 칸 안에 들어갈 지시문", example = "직접 입력")
        private String setting;
        @ApiModelProperty(name = "답변1", example = "5")
        private String type;
        @ApiModelProperty(name = "지시문1", example = "평")
        private String description;

        public Answer (String setting, String description) {
            this.setting = setting;
            this.description = description;
        }
    }

    public static List<Answer> fromQuestion(CommonQuestionTypeB question) {

        List<Answer> list = new ArrayList<>();
        if (question.getDirection1() != null) {
            list.add(new Answer(question.getSetting(), question.getDirection1()));
        }
        if (question.getDirection2() != null) {
            list.add(new Answer(question.getSetting(), question.getDirection2()));
        }

        return list;
    }

    public static List<Answer> fromAnswer(CommonQuestionTypeB question, List<com.example.amattang.domain.answer.Answer> answer, Long id) {

        List<Answer> list = new ArrayList<>();
        for (com.example.amattang.domain.answer.Answer a : answer) {
            list.add(new Answer(question.getSetting(), ((AnswerString) a).getAns(), a.getType()));
        }

        return list;
    }
}
