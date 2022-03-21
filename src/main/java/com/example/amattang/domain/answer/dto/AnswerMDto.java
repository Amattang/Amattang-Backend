package com.example.amattang.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class AnswerMDto {

    private String address;
    private Map<String, String> latlng;

    public static List<AnswerMDto> fromEntity(String address, String lat, String lon) {
        return Arrays.asList(
                new AnswerMDto(
                    address,
                    new HashMap<>() {{
                        put("latitude", lat);
                        put("longitude", lon);
                    }}
                )
        );
    }
}
