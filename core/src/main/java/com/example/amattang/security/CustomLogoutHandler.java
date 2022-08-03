package com.example.amattang.security;

import com.example.amattang.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements ServerLogoutHandler {

    private final RedisTemplate redisTemplate;

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        String userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        redisTemplate.delete(userId);
        //return and mono.empty
        return Mono.empty();
    }

}
