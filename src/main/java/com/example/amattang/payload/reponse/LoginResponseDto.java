package com.example.amattang.payload.reponse;

import com.example.amattang.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.Locale;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    @ApiModelProperty(name = "토큰 타입", example = "Bearer")
    private final String tokenType = "Bearer";

    @ApiModelProperty(name = "사용자 아이디", example = "KAKAO_12345")
    private String id;
    @ApiModelProperty(name = "사용자 이름/닉네임", example = "jaeri")
    private String name;
    @ApiModelProperty(name = "사용자 메일", example = "jaeri@gmail.com")
    private String email;
    @ApiModelProperty(name = "인증 기관", example = "kakao")
    private String provider;
    @ApiModelProperty(name = "access token")
    private String accessToken;

    @ApiModelProperty(name = "access token 남은 유효 시간 (밀리초)", example = "14400000")
    private long accessExpiresIn;
    @ApiModelProperty(name = "refresh token")
    private String refreshToken;

    @ApiModelProperty(name = "refresh token 남은 유효 시간 (밀리초)", example = "2592000000")
    private long refreshExpiresIn;

    public static LoginResponseDto create(User user, String access, String refresh, Date date) {
        Date now = new Date();
        LoginResponseDto res = LoginResponseDto.builder()
                .id(user.getId())
                .provider(user.getProvider().name().toLowerCase())
                .accessToken(access)
                .accessExpiresIn(14400000L - (now.getTime() - date.getTime()))
                .refreshToken(refresh)
                .refreshExpiresIn(2592000000L - (now.getTime() - date.getTime()))
                .build();
        if (user.getProvider().equals(User.PROVIDER.KAKAO)) {
            res.setName(user.getName());
        } else {
            res.setEmail(user.getName());
        }

        return res;
    }

}
