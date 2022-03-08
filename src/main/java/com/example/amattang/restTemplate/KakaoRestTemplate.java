package com.example.amattang.restTemplate;

import com.example.amattang.payload.reponse.KakaoUserInfoReponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class KakaoRestTemplate {

    private final String baseUri = "https://kapi.kakao.com";

    HttpEntity httpEntity(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return new HttpEntity(httpHeaders);
    }

    public KakaoUserInfoReponseDto getKakaoUserNickName(String token) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoUserInfoReponseDto> exchange = restTemplate.exchange(baseUri + "/v2/user/me", HttpMethod.GET, httpEntity(token), new ParameterizedTypeReference<KakaoUserInfoReponseDto>() {
        });
        return exchange.getBody();
    }
}
