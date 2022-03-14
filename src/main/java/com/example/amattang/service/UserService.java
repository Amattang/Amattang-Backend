package com.example.amattang.service;

import com.example.amattang.domain.user.User;
import com.example.amattang.payload.reponse.LoginResponseDto;
import com.example.amattang.payload.reponse.ReIssueTokenResponseDto;
import com.example.amattang.payload.request.LoginRequestDto;
import com.example.amattang.payload.request.ReIssueTokenRequestDto;
import com.example.amattang.template.RedisCustomTemplate;
import com.example.amattang.security.CustomUserDetailsService;
import com.example.amattang.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        User user = userDetailsService.registerNewUser(dto.getAccessToken(), dto.getProvider());
        Date date = new Date();
        String accessToken = tokenProvider.createJwtAccessToken(user.getId(),date);
        String refreshToken = tokenProvider.createJwtRefreshToken(date);
        redisCustomTemplate.timeoutTemplate(refreshToken, user.getId(), refreshTokenExpirationSec, TimeUnit.MICROSECONDS);
        return LoginResponseDto.create(user, accessToken, refreshToken, date);
    }

    public ReIssueTokenResponseDto reIssueToken(ReIssueTokenRequestDto dto) {
        String request = dto.getRequest().toLowerCase();
        String token = tokenProvider.getJwtAccessFromFullToken(dto.getToken());
        if (request.equals("access")) {
            return reIssueAccessToken(dto.getToken());
        } else if (request.equals("refresh")) {
            return reIssueRefreshToken(dto.getToken());
        }
        throw new IllegalArgumentException("요청 토큰 형식이 잘못되었습니다.");
    }

    public ReIssueTokenResponseDto reIssueAccessToken(String refresh) {
        if (tokenProvider.validateToken(refresh) && redisTemplate.hasKey(refresh)) {
            String userId = redisCustomTemplate.getRedisStringValue(refresh);
            Date date = new Date();
            String token = tokenProvider.createJwtAccessToken(userId, date);
            return ReIssueTokenResponseDto.create(token, "access", date);
        }
        throw new IllegalArgumentException("만료된 토큰입니다.");
    }

    public ReIssueTokenResponseDto reIssueRefreshToken(String access) {
        if (tokenProvider.validateToken(access)) {
            String userId = tokenProvider.getUserIdFromToken(access);
            Date date = new Date();
            String jwtRefreshToken = tokenProvider.createJwtRefreshToken(date);
            redisCustomTemplate.timeoutTemplate(jwtRefreshToken, userId, refreshTokenExpirationSec, TimeUnit.MICROSECONDS);
            return ReIssueTokenResponseDto.create(jwtRefreshToken, "refresh", date);
        }
        throw new IllegalArgumentException("만료된 토큰입니다.");
    }
}
