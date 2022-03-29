package com.example.amattang.domain.answer.dto;

import com.example.amattang.domain.commonQuestion.CommonQuestionTypeC;
import com.example.amattang.domain.image.Image;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
