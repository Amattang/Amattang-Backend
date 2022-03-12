package com.example.amattang.domain.answer.dto;

import com.example.amattang.payload.reponse.CommonCheckListDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

public class AnswerCDto {

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
