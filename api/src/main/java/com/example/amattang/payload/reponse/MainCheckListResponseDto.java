package com.example.amattang.payload.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "체크리스트 목록 반환값")
public class MainCheckListResponseDto {

    @ApiModelProperty(name = "체크리스트 아이디", example = "1", required = true)
    private Long id;

    @ApiModelProperty(name = "체크리스트 메인 화면 고정 여부", example = "true")
    private boolean pinned;

    private List<BasicQuestion> question;

    @Data
    @ApiModel(description = "메인 화면에서 기본정보를 구성하기 위한 질문에 대한 답과 타입")
    class BasicQuestion {
        @ApiModelProperty(name = "기본정보 질문에 대한 답", example = "7평 원룸 골목 식당가")
        private String ans;

        @ApiModelProperty(name = "기본정보 타입", example = "address")
        private String type;
    }

}
