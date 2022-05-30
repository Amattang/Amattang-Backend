package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.answer.AnswerBool;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeD;
import com.example.amattang.domain.commonQuestion.QuestionTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class AnswerDDto {

    @ApiModelProperty(value = "답변 아이디", example = "45")
    private Long id;

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

    public static List<Answer> fromQuestion(CommonQuestionTypeD question) {
        List<Answer> answer = new ArrayList<>();
        List<QuestionTemplate> templates = question.getTemplates();
        Collections.sort(templates, (n, m) -> n.getId().compareTo(m.getId()));
        for (QuestionTemplate q : templates) {
            answer.add(new Answer(q.getTemplate(), false));
        }
        return answer;
    }

    public static List<Answer> fromAnswer(CommonQuestionTypeD question, List<com.example.amattang.domain.answer.Answer> answer, Long id) {
        List<Answer> list = new ArrayList<>();
        Collections.sort(question.getTemplates(), (n, m) -> n.getId().compareTo(m.getId()));

        for (com.example.amattang.domain.answer.Answer a : answer) {
            list.add(new Answer(a.getType(), ((AnswerBool) a).isAns()));
        }

        return list;
    }
}
