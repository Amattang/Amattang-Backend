package com.example.amattang.restTemplate;

import com.example.amattang.restTemplate.response.KakaoUserInfoReponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class KakaoRestTemplate {

    private final String baseUri = "https://kapi.kakao.com";

    HttpEntity httpEntity(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return new HttpEntity(httpHeaders);
    }

    public Optional<KakaoUserInfoReponseDto> getKakaoUserNickName(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KakaoUserInfoReponseDto> exchange = restTemplate.exchange(baseUri + "/v2/user/me", HttpMethod.GET, httpEntity(token), new ParameterizedTypeReference<KakaoUserInfoReponseDto>() {
            });
            return Optional.of(exchange.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.ofNullable(null);
        }
    }
}
