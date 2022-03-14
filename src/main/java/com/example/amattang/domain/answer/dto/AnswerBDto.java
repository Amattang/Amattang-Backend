package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.answer.Answer;
import com.example.amattang.domain.answer.AnswerBool;
import com.example.amattang.domain.answer.AnswerString;
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
    private Long id;

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

    public static List<Answer> fromQuestion(CommonQuestionTypeB question) {

        List<Answer> list = new ArrayList<>();
        if (question.getDirection1() != null && StringUtils.hasText(question.getDirection1())) {
            list.add(new Answer(question.getDirection1()));
        }
        if (question.getDirection2() != null && StringUtils.hasText(question.getDirection2())) {
            list.add(new Answer(question.getDirection2()));
        }

//        return AnswerBDto.builder()
//                .ans(list)
//                .build();

        return list;
    }

    public static List<Answer> fromAnswer(CommonQuestionTypeB question, List<com.example.amattang.domain.answer.Answer> answer, Long id) {

        List<Answer> list = new ArrayList<>();
        for (com.example.amattang.domain.answer.Answer a : answer) {
            list.add(new Answer(a.getType(), ((AnswerString) a).getAns()));
        }


//        return AnswerBDto.builder()
//                .id(id)
//                .ans(list)
//                .build();
        return list;
    }
}
