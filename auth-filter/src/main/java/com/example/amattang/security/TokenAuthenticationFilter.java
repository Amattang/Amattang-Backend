package com.example.amattang.security;

import com.example.amattang.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements WebFilter {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        log.debug("do filter => token authentication filter");

        try {

            if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                String accessToken = tokenProvider.getJwtAccessFromFullToken(request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));

                if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
                    String userId = tokenProvider.getUserIdFromToken(accessToken);

                    UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }
}
