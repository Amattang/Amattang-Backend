package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.commonQuestion.CommonQuestionTypeC;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@AllArgsConstructor
public class AnswerCDto {

    @ApiModelProperty(value = "답변 아이디", example = "11")
    private Long answerId;

    List<Answer> answer;

    @ApiModel(value = "ctype 답변")
    @Data
    @AllArgsConstructor
    static class Answer {
        @ApiModelProperty(name = "답변 지시문", example = "손상")
        private String type;

        @ApiModelProperty(name = "답변", example = "true")
        private boolean val;

        @ApiModelProperty(name = "버튼 색깔이 빨강색인가?", example = "true")
        private boolean redType;

        public Answer(String type, boolean redType) {
            this.type = type;
            this.redType = redType;
        }
    }

    public static AnswerCDto fromQuestion(CommonQuestionTypeC question) {
        Answer answer1 = new Answer("손상", (question.getRedType().equals(REDTYPE.TRUE) ? true : false));
        Answer answer3 = new Answer("해당 없음", false);
        ArrayList list = new ArrayList(Arrays.asList(answer1, answer3));
        if (question.getType2Exists()) {
            list.add(new Answer("더러움", (question.getRedType().equals(REDTYPE.TRUE)? true : false)));
        }
        return AnswerCDto.builder()
                .answer(list)
                .build();
    }
}
