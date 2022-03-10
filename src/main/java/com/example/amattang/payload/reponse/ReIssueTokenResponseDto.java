package com.example.amattang.payload.reponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReIssueTokenResponseDto {

    @ApiModelProperty(value = "토큰 타입", example = "Bearer")
    private final String type = "Bearer";

    @ApiModelProperty(value = "토큰 값")
    private String token;

    @ApiModelProperty(value = "token 남은 유효 시간 (밀리초)", example = "25348888")
    private long expiresIn;

    public static ReIssueTokenResponseDto create(String token, String request, Date date) {
        Date now = new Date();
        return new ReIssueTokenResponseDto(
                token,
                ((request.equals("access")) ? 14400000L : 2592000000L) - (now.getTime() - date.getTime())
        );
    }
}
