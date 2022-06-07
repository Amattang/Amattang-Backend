package com.example.amattang.config;

import com.example.amattang.domain.user.User;
import com.example.amattang.security.CustomLogoutHandler;
import com.example.amattang.security.CustomUserDetailsService;
import com.example.amattang.security.RestAuthenticationEntryPoint;
import com.example.amattang.security.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLogoutHandler logoutHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public ReactiveAuthenticationManager authenticationManagerBean() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        return manager;
    }

//    private final DataSource dataSource;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource);
//    }
//

//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/**/swagger-resources/**", "/**/swagger-resources",
//                "/v2/api-docs", "/webjars/**", "/swagger-ui.html");
//    }
//

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity httpSecurity) {

        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeExchange()
                .pathMatchers(
                        "/error"
                )
                .permitAll()
                .pathMatchers("/login","/issue/re", "/api/any")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/check-list/{checkListId}/common", "/api/check-list/{checkListId}/custom")
                .permitAll()
                .pathMatchers("/api/check-list/**", "/api/role")
                .hasRole(User.ROLE.USER.name())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutHandler(logoutHandler)
                .logoutSuccessHandler(new RedirectServerLogoutSuccessHandler());

        httpSecurity.addFilterBefore(tokenAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return httpSecurity.build();

    }

}
