package com.example.amattang.payload.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomCheckListResponseDto {
    @ApiModelProperty(name = "커스텀 카테고리 아이디", example = "1")
    private Long categoryId;
    @ApiModelProperty(name = "커스텀 카테고리 이름", example = "집주인한테 물어볼 것")
    private String categoryName;

    private List<CustomQuestion> questions;

    @Data
    @ApiModel(value = "커스텀 카테고리 하위 목록")
    class CustomQuestion {
        @ApiModelProperty(name = "하위 항목 아이디", example = "1")
        private Long questionId;
        @ApiModelProperty(name = "하위 항목 내용", example = "원래 입주일보다 앞당겨서 입주 가능한가요?")
        private String content;
        @ApiModelProperty(name = "하위 항목 체크 여부", example = "true")
        private boolean checked;
    }
}
