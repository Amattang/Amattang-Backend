package com.example.amattang.payload.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MainCheckListRequestDto {

    @Valid
    private List<MainRequest> checkList;

    @Data
    public static class MainRequest {
        @NotNull(message = "체크리스트 아이디가 누락되었습니다.")
        private Long id;
        @NotNull(message = "체크리스트 핀 여부가 누락되었습니다.")
        private Boolean pinned;
    }

}
