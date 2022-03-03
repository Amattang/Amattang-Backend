package com.example.amattang.payload.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommonCheckListDto {

    @ApiModelProperty(name = "질문 아이디", example = "1")
    private String questionId;
    @ApiModelProperty(name = "질문 내용", example = "맞은편 집과의 거리가 가깝나요?")
    private String question;
    @ApiModelProperty(name = "1차 카테고리", example = "내부 시설")
    private String mainCategory;
    @ApiModelProperty(name = "2차 카테고리", example = "창문")
    private String subCategory;
    @ApiModelProperty(name = "행동 요령", example = "창문을 완전히 열어보세요")
    private String rule;
    @ApiModelProperty(name = "항목 설명", example = "반지하층~1.5층은 방범창 또는 철장을 요구할 수 있어요")
    private String description;
    @ApiModelProperty(name = "이모지", example = ":)")
    private String emoji;
    @ApiModelProperty(name = "답변 타입", example = "B")
    private String type;
    private AnswerA answerA;
    private AnswerB answerB;
    private AnswerC answerC;
    private AnswerD answerD;

    @Data
    class AnswerA{
        @ApiModelProperty(value = "답변 아이디", example = "1")
        private Long answerId;

        @ApiModelProperty(name = "답변", example = "true")
        private boolean ans;

        @ApiModelProperty(name = "어떤 경우 빨간색으로 표시하면 되는가?", example = "true")
        private boolean redType;
    }

    @Data
    class AnswerB{
        @ApiModelProperty(value = "답변 아이디", example = "4")
        private Long answerId;

        @ApiModelProperty(name = "답변1", example = "학교")
        private String ans1;

        @ApiModelProperty(name = "답변2", example = "도보 10")
        private String ans2;

        @ApiModelProperty(name = "지시문1", example = "까지")
        private String direct1;

        @ApiModelProperty(name = "지시문2", example = "분")
        private String direct2;

    }
    @Data
    class AnswerC{
        @ApiModelProperty(value = "답변 아이디", example = "11")
        private Long answerId;

        List<Type> answer;

        @ApiModel(value = "ctype 답변")
        @Data
        class Type {
            @ApiModelProperty(name = "답변 지시문", example = "손상")
            private String type;

            @ApiModelProperty(name = "답변", example = "true")
            private boolean val;
        }

    }
    @Data
    class AnswerD{
        @ApiModelProperty(value = "답변 아이디", example = "45")
        private Long answerId;

        List<TypeD> answer;
        @Data
        @ApiModel(value = "dtype 답변")
        class TypeD {
            @ApiModelProperty(name = "답변 지시문", example = "남향")
            private String type;
            @ApiModelProperty(name = "답변", example = "true")
            private boolean val;
        }
    }
}
