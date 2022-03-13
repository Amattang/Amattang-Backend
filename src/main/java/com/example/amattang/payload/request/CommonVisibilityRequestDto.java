package com.example.amattang.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommonVisibilityRequestDto {

    @NotNull(message = "삭제(추가)할 질문 아이디가 누락되었습니다.")
    @ApiModelProperty(name = "삭제(추가)할 질문 아이디", example = "1")
    private Long id;

    @NotNull(message = "질문 삭제 여부가 누락되었습니다.")
    @ApiModelProperty(name = "질문 삭제 여부", example = "true")
    private Boolean visibility;
}
