package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.commonQuestion.CommonQuestionTypeD;
import com.example.amattang.domain.commonQuestion.QuestionTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AnswerDDto {

    @ApiModelProperty(value = "답변 아이디", example = "45")
    private Long answerId;

    List<Answer> answer;

    @Data
    @AllArgsConstructor
    @ApiModel(value = "dtype 답변")
    static class Answer {
        @ApiModelProperty(name = "답변 지시문", example = "남향")
        private String type;
        @ApiModelProperty(name = "답변", example = "true")
        private boolean val;
    }

    public static AnswerDDto fromQuestion(CommonQuestionTypeD question) {
        List<Answer> answer = new ArrayList<>();
        for (QuestionTemplate q : question.getTemplates()) {
            answer.add(new Answer(q.getTemplate(), false));
        }
        return AnswerDDto.builder()
                .answer(answer)
                .build();
    }
}
