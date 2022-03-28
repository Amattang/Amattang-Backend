package com.example.amattang.payload.reponse;

import com.example.amattang.domain.checkList.CheckList;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommonCheckListDto {

    @ApiModelProperty(name = "사용자의 체크리스트 아이디", example = "1")
    private Long checkListId;


    private List<CommonQuestionDto> questionList;

    public static CommonCheckListDto create(CheckList checkList, List<CommonQuestionDto> question) {
        return CommonCheckListDto.builder()
                .checkListId(checkList.getId())
                .questionList(question)
                .build();

    }
}
