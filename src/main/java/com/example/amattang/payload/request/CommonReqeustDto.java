package com.example.amattang.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class CommonReqeustDto {

    private Long categoryId;
    private List<CommonAnswerDto> answer;

    @Data
    class CommonAnswerDto {
//        private Long questionId;
//        private
    }
}
