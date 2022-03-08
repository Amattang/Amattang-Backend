package com.example.amattang.payload.reponse;

import com.example.amattang.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

    @ApiModelProperty(name = "사용자 아이디", example = "KAKAO_12345")
    private String id;
    @ApiModelProperty(name = "사용자 이름/닉네임", example = "jaeri")
    private String name;
    @ApiModelProperty(name = "인증 기관", example = "kakao")
    private String provider;
    @ApiModelProperty(name = "access token")
    private String accessToken;
    @ApiModelProperty(name = "refresh token")
    private String refreshToken;

    public static LoginResponseDto create(User user, String access, String refresh) {
        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getProvider().name().toLowerCase(),
                access,
                refresh
        );
    }

}
