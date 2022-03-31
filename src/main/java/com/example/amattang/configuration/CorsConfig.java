package com.example.amattang.configuration;

import com.example.amattang.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer, Filter {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        log.info("Request API URI => [" + request.getMethod() + "] " + request.getRequestURI() + ((request.getQueryString() != null) ?
                URLDecoder.decode(request.getQueryString(), "UTF-8") : " "));
        String contentType = request.getContentType();
        log.info("Request Content Type => " + contentType);
        if (Optional.ofNullable(contentType).isPresent() && request.getContentType().contains("json")) {
            RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper(request);
            ServletInputStream inputStream = rereadableRequestWrapper.getInputStream();
            log.info("Request Body : " + StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));

        }

        if (Optional.ofNullable(request.getHeader("Authorization")).isPresent()) {
            String token = tokenProvider.getJwtAccessFromHeader(request);
            String userId = tokenProvider.getUserIdFromToken(token);

            log.info("Request User Id => "+userId);
        } else {
            log.info("Token Not Found");
        }

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Origin, Access-Control-Allow-Origin, content-type, Authorization");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }


}
