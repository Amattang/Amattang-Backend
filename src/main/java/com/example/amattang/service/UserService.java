package com.example.amattang.service;

import com.example.amattang.domain.user.User;
import com.example.amattang.payload.reponse.LoginResponseDto;
import com.example.amattang.payload.request.LoginRequestDto;
import com.example.amattang.redisTemplate.RedisCustomTemplate;
import com.example.amattang.security.CustomUserDetailsService;
import com.example.amattang.security.TokenProvider;
import com.example.amattang.security.TokenProvider.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.token.refreshTokenExpirationSec}")
    private long refreshTokenExpirationSec;
    private final CustomUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final RedisCustomTemplate redisCustomTemplate;

    public LoginResponseDto loadUser(LoginRequestDto dto) {
        User user = userDetailsService.registerNewUser(dto.getAccessToken(), dto.getProvider());
        String accessToken = tokenProvider.createJwtToken(user, TokenType.AUTHORIZATION);
        String refreshToken = tokenProvider.createJwtToken(user, TokenType.REFRESH);
        redisCustomTemplate.timeoutTemplate(user.getId(), refreshToken, refreshTokenExpirationSec, TimeUnit.MICROSECONDS);
        return LoginResponseDto.create(user, accessToken, refreshToken);
    }
}
