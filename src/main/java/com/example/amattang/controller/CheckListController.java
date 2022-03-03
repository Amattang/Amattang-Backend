package com.example.amattang.controller;

import com.example.amattang.payload.reponse.MainCheckListResponseDto;
import com.example.amattang.payload.reponse.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.amattang.payload.reponse.ResponseMessage.GET_USER_ALL_CHECK_LIST;

@RestController
@RequestMapping("/api/check-list")
public class CheckListController {

    @ApiOperation(value = "1-1.체크리스트 목록(기본정보) 반환", response = MainCheckListResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message="체크리스트의 아이디, 기본정보를 반환", response = MainCheckListResponseDto.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity<?> doc() {
        return ResponseUtil.succes(new MainCheckListResponseDto(), GET_USER_ALL_CHECK_LIST);
    }
}
