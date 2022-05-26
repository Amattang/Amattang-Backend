package com.example.amattang.payload.reponse;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class KakaoUserInfoReponseDto {

    private Long id;
    private HashMap<String, String> properties;

}
