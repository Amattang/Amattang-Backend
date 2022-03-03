package com.example.amattang.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;


@Data
public class CustomRequestDto {

    @ApiModelProperty(name = "카테고리 아이디", example = "1", notes = "put 요청시에만 포함")
    private Long id;

    @ApiModelProperty(name = "카테고리 이름", example = "집주인한테 물어볼 것들")
    private String categoryName;
    
    List<QuestionDto> questions;

    @Data
    @ApiModel(description = "custom 카테고리의 하위 항목")
    class QuestionDto {
        @ApiModelProperty(name = "카테고리 하위 항목", example = "원래 날짜보다 앞당겨서 입주가능한가요?")
        private String content;
        @ApiModelProperty(name = "카테고리 하위 항목 체크 여부", example = "false")
        private boolean checked;
    }
}
