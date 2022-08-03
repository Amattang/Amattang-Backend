package com.example.amattang.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebFlux
@RequiredArgsConstructor
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("x-requested-with", "Origin", "Access-Control-Allow-Origin", "content-type", "Authorization")
                .exposedHeaders("Authorization")
                .allowedOrigins("http://localhost:8080", "http://localhost:8000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .maxAge(3600);

    }
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest request = (HttpServletRequest) req;
//
//        log.info("Request API URI => [" + request.getMethod() + "] " + request.getRequestURI() + ((request.getQueryString() != null) ?
//                URLDecoder.decode(request.getQueryString(), "UTF-8") : " "));
//        String contentType = request.getContentType();
//        log.info("Request Content Type => " + contentType);
//
//        if (StringUtils.hasText(request.getHeader(AUTHORIZATION))) {
//            String token = tokenProvider.getJwtAccessFromFullToken(request.getHeader(AUTHORIZATION));
//            String userId = tokenProvider.getUserIdFromToken(token);
//
//            log.info("Request User Id => "+userId);
//        } else {
//            log.info("Token Not Found");
//        }
//
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Origin, Access-Control-Allow-Origin, content-type, Authorization");
//        response.setHeader("Access-Control-Expose-Headers", "Authorization");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            chain.doFilter(req, res);
//        }
//    }
}
