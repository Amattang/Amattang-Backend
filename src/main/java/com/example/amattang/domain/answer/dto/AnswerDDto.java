package com.example.amattang.domain.answer.dto;

import com.example.amattang.payload.reponse.CommonCheckListDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

public class AnswerDDto {

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
