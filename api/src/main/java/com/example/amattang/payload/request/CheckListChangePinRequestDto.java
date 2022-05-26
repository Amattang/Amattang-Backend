package com.example.amattang.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckListChangePinRequestDto {

    @NotNull(message = "체크리스트 아이디가 누락되었습니다.")
    private Long checkListId;

    @NotNull(message = "체크리스트 핀 여부가 누락되었습니다.")
    private Boolean pinned;

}
