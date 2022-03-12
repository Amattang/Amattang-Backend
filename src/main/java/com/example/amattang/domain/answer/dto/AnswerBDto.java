package com.example.amattang.domain.answer.dto;

import io.swagger.annotations.ApiModelProperty;

public class AnswerBDto {

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
