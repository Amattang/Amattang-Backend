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
import javax.servlet.http.Part;
import java.util.Optional;

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
        log.debug("\n request origin uri => [" +  request.getMethod() + "] " + urlPathHelper.getOriginatingRequestUri(request) + "\n");

        log.debug("\n request content type => " + request.getContentType() + " \n");
        try {
            Part image = request.getPart("image");
            log.debug("multipart : " + image.getContentType());
        } catch (Exception e) {
            log.warn("multipart not found");
        }

        if (Optional.ofNullable(request.getHeader("Authorization")).isPresent()) {
            String token = tokenProvider.getJwtAccessFromHeader(request);
            String userId = tokenProvider.getUserIdFromToken(token);

            log.debug("\n user name => "+userId+ "\n");
        } else {
            log.debug("\n token not found \n");
        }

    }
}
