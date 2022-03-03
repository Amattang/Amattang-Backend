package com.example.amattang.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonVisibilityRequestDto {

    @ApiModelProperty(name = "삭제(추가)할 질문 아이디", example = "1")
    private Long id;
    @ApiModelProperty(name = "질문 삭제 여부", example = "true")
    private Boolean visibility;
}
