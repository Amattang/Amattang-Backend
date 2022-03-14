package com.example.amattang.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ReIssueTokenRequestDto {

    @ApiModelProperty(value = "요청하는 토큰", example = "access / refresh")
    @NotBlank(message = "요청하는 토큰 형식이 누락되었습니다.")
    private String request;

    @NotBlank(message = "토큰 값이 누락되었습니다.")
    @Pattern(regexp = "Bearer.*", message = "Bearer 타입의 토큰이 필요합니다.")
    @ApiModelProperty(value = "토큰 값", example = "Bearer + 토큰 값 (사이 공백)")
    private String token;

}
