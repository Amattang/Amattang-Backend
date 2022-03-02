package com.example.amattang.controller;


import com.example.amattang.payload.reponse.CustomCheckListResponseDto;
import com.example.amattang.payload.request.CustomQuestionRequestDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-list/{checkListId}/custom")
public class CustomQuestionController {

    @ApiOperation(value = "3-1. 전체 카테고리와 하위 항목 반환", response = CustomCheckListResponseDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
    })
    @GetMapping
    public List<CustomCheckListResponseDto> doc2(@PathVariable("checkListId") Long checkListId) {

         return null;
    }

    @ApiOperation(value = "3-2. 카테고리,질문,체크여부 한번에 받아서 저장", notes = "카테고리 하위 항목들을 id를 따져가면서 받기에 힘들 수도 있을 듯 -> 그냥 다 새로 저장할까...")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PostMapping
    public void doc(@PathVariable("checkListId") Long checkListId, @RequestBody CustomQuestionRequestDto dto) {

    }

    @ApiOperation(value = "3-3. 카테고리, 질문, 체크여부 한번에 받아서 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PutMapping
    public void doc2(@PathVariable("checkListId") Long checkListId, @RequestBody CustomQuestionRequestDto dto) {

    }


    @ApiOperation(value = "3-4. 카테고리 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
    })
    @DeleteMapping
    public void doc11(@PathVariable("checkListId") Long checkListId) {

    }

    @ApiOperation(value = "3-5. 카테고리의 상세 항목 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "커스텀 카테고리의 하위 항목 아이디 리스트", example = "3", required = true, paramType = "params")
    })
    @DeleteMapping("/question")
    public void doc4(@PathVariable("checkListId") Long checkListId, @RequestParam("id") List<Long> id) {

    }

}
