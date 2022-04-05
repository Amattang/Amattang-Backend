package com.example.amattang.payload.reponse;

import com.example.amattang.domain.commonQuestion.CommonQuestion;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonQuestionDto {

    @ApiModelProperty(name = "질문 아이디", example = "1")
    private Long questionId;
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
    @ApiModelProperty(name = "이모지", example = "smile")
    private String emoji;
    @ApiModelProperty(name = "답변 타입", example = "B")
    private String type;
    @ApiModelProperty(name = "질문 삭제 여부", example = "true")
    private boolean visibility;

    @ApiModelProperty(name = "질문에 대한 답변", notes = "답변이 없는 경우에도 초기화하여 반환, 각 타입은 아래 model에서 확인, AnswerADto, AnswerBDto, AnswerCDto, AnswerDDto")
    private List answer;

    public static CommonQuestionDtoBuilder fromQuestionBuilder(CommonQuestion question, List answer) {
        return CommonQuestionDto.builder()
                .questionId(question.getId())
                .question(question.getQuestion())
                .mainCategory(question.getMainCategory())
                .subCategory(question.getSubCategory())
                .rule(question.getRule())
                .description(question.getDescription())
                .emoji(question.getEmoji())
                .type(question.getAnsType())
                .answer(answer);
    }

    public static CommonQuestionDto fromQuestion(CommonQuestion question, List answer, boolean visibility) {
        return fromQuestionBuilder(question, answer)
                .visibility(visibility)
                .build();
    }

}
