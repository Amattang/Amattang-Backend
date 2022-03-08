package com.example.amattang.payload.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequestDto {

    @ApiModelProperty(name = "access token")
    private String accessToken;

    @ApiModelProperty(name = "provider", example = "kakao")
    private String provider;

}
