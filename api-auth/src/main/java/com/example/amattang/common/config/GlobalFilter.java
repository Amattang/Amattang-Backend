package com.example.amattang.common.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.ConfigField> {

    public GlobalFilter() {
    }

    public static class ConfigField{
        private String baseMessage; //로그 항목에 포함될 사용자 지정 메세지
        private boolean preLogger; //요청을 전달하기 전 필터 기록여부를 나타내는 플래그
        private boolean postLogger; //프록시 된 서비스에서 응답을 받은 후 필터 기록여부를 나타내는 플래그
    }

    @Override
    public GatewayFilter apply(ConfigField config) {
        return null;
    }
}
