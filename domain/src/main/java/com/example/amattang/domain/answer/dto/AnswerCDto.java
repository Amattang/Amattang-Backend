package com.example.amattang.domain.answer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AnswerCDto {

    @ApiModelProperty(value = "이미지 아이디", example = "4")
    private Long id;

    private int order;

    private String url;

    private boolean main;

}
