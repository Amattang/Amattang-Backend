package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.commonQuestion.CommonQuestionTypeB;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AnswerBDto {

    @ApiModelProperty(value = "답변 아이디", example = "4")
    private Long answerId;

    private List<Answer> ans;

    @Getter
    @AllArgsConstructor
    static class Answer {
        @ApiModelProperty(name = "답변1", example = "5")
        private String type;
        @ApiModelProperty(name = "지시문1", example = "평")
        private String description;

        public Answer (String description) {
            this.description = description;
        }
    }

    public static AnswerBDto fromQuestion(CommonQuestionTypeB question) {

        List<Answer> list = new ArrayList<>();
        if (question.getDirection1() != null && StringUtils.hasText(question.getDirection1())) {
            list.add(new Answer(question.getQuestion()));
        }
        if (question.getDirection2() != null && StringUtils.hasText(question.getDirection2())) {
            list.add(new Answer(question.getQuestion()));
        }

        return AnswerBDto.builder()
                .ans(list)
                .build();
    }
}
