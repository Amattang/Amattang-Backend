package com.example.amattang.domain.answer.dto;

import io.swagger.annotations.ApiModelProperty;

public class AnswerADto {

    @ApiModelProperty(value = "답변 아이디", example = "1")
    private Long answerId;

    @ApiModelProperty(name = "답변", example = "true")
    private boolean ans;

    @ApiModelProperty(name = "어떤 경우 빨간색으로 표시하면 되는가?", example = "true")
    private boolean redType;


}
