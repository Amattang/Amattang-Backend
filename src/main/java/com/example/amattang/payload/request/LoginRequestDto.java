package com.example.amattang.payload.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {

    @NotBlank(message = "액세스 토큰이 누락되었습니다.")
    @ApiModelProperty(name = "access token")
    private String accessToken;

    @NotBlank(message = "인증 기관명이 누락되었습니다.")
    @ApiModelProperty(name = "provider", example = "kakao")
    private String provider;

}
