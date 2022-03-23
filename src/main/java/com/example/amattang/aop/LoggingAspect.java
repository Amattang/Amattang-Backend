package com.example.amattang.aop;

import com.example.amattang.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final TokenProvider tokenProvider;

    @Before("execution(* com.example.amattang.controller.*.*(..))")
    public void LoggingFromRequest() {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        log.debug("\n api request\n");
        log.debug("\n request origin uri => " + urlPathHelper.getOriginatingRequestUri(request) + "\n");

        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            String token = tokenProvider.getJwtAccessFromHeader(request);
            String userId = tokenProvider.getUserIdFromToken(token);

            log.debug("\n user name => "+userId+ "\n");
        } else {
            log.debug("\n token not found \n");
        }

    }
}
