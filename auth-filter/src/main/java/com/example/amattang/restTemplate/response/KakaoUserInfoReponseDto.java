package com.example.amattang.restTemplate.response;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class KakaoUserInfoReponseDto {

    private Long id;
    private HashMap<String, String> properties;

}
