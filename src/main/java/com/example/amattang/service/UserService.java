package com.example.amattang.service;

import com.example.amattang.domain.user.User;
import com.example.amattang.payload.reponse.LoginResponseDto;
import com.example.amattang.payload.reponse.ReIssueTokenResponseDto;
import com.example.amattang.payload.request.AppleLoginRequestDto;
import com.example.amattang.payload.request.LoginRequestDto;
import com.example.amattang.payload.request.ReIssueTokenRequestDto;
import com.example.amattang.security.CustomUserDetailsService;
import com.example.amattang.security.TokenProvider;
import com.example.amattang.template.RedisCustomTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.example.amattang.security.TokenProvider.TokenType.ACCESS;
import static com.example.amattang.security.TokenProvider.TokenType.REFRESH;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.token.refreshTokenExpirationSec}")
    private long refreshTokenExpirationSec;
    private final CustomUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final RedisCustomTemplate redisCustomTemplate;
    private final RedisTemplate redisTemplate;

    public LoginResponseDto loadUser(LoginRequestDto dto) {
        User user = userDetailsService.registerNewKakaoUser(dto.getAccessToken(), dto.getProvider());
        return makeTokenWithUser(user);
    }

    public LoginResponseDto loadUser(AppleLoginRequestDto dto) {
        User user = userDetailsService.registerNewAppleUser(dto.getUser(), dto.getEmail());
        return makeTokenWithUser(user);
    }

    public LoginResponseDto makeTokenWithUser(User user) {
        Date date = new Date();
        String accessToken = tokenProvider.createJwtToken(user.getId(),date, ACCESS);
        String refreshToken = tokenProvider.createJwtToken(user.getId(),date, REFRESH);
        redisCustomTemplate.timeoutTemplate(user.getId(), refreshToken, refreshTokenExpirationSec, TimeUnit.MICROSECONDS);
        return LoginResponseDto.create(user, accessToken, refreshToken, date);
    }

    public ReIssueTokenResponseDto reIssueToken(ReIssueTokenRequestDto dto) {
        String request = dto.getRequest().toLowerCase();
        String token = tokenProvider.getJwtAccessFromFullToken(dto.getToken());
        if (request.equals("access")) {
            return reIssueAccessToken(token);
        }
        throw new IllegalArgumentException("요청 토큰 형식이 잘못되었습니다.");
    }

    public ReIssueTokenResponseDto reIssueAccessToken(String refresh) {
        String userId = tokenProvider.getUserIdFromToken(refresh);
        if (tokenProvider.validateToken(refresh) && redisTemplate.hasKey(userId)) {
            String redisToken = redisCustomTemplate.getRedisStringValue(userId);
            if (redisToken.equals(refresh)) {
                Date date = new Date();
                String token = tokenProvider.createJwtToken(userId,date, ACCESS);
                return ReIssueTokenResponseDto.create(token, "access", date);
            }
        }
        throw new IllegalArgumentException("로그인을 해주세요.");
    }

}
