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

//    @JsonProperty("check_list_id")
    @ApiModelProperty(name = "사용자의 체크리스트 아이디", example = "1")
    private Long checkListId;

    @ApiModelProperty(name = "현재 체크리스트의 메인 화면 고정 여부", example = "true")
    private boolean pinned;

    private List<CommonQuestionDto> questionList;

    public static CommonCheckListDto create(CheckList checkList, List<CommonQuestionDto> question) {
        return CommonCheckListDto.builder()
                .checkListId(checkList.getId())
                .pinned(checkList.getPinned())
                .questionList(question)
                .build();

    }
}
