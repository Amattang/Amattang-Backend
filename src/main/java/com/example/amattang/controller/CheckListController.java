package com.example.amattang.controller;

import com.example.amattang.payload.reponse.MainCheckListResponseDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check-list")
public class CheckListController {

    @ApiOperation(value = "1-1.체크리스트 목록(기본정보) 반환", response = MainCheckListResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message="체크리스트의 아이디, 기본정보를 반환", response = MainCheckListResponseDto.class, responseContainer = "List")
    })
    @GetMapping
    public MainCheckListResponseDto doc() {

        return new MainCheckListResponseDto();
    }
}
