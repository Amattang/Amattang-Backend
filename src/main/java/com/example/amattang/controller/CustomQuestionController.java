package com.example.amattang.controller;


import com.example.amattang.payload.reponse.CustomCheckListResponseDto;
import com.example.amattang.payload.reponse.ResponseMessage;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.payload.request.CustomRequestDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static com.example.amattang.payload.reponse.ResponseUtil.succes;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/check-list/{checkListId}/custom")
public class CustomQuestionController {

    @ApiOperation(value = "3-1. 전체 카테고리와 하위 항목 반환", response = CustomCheckListResponseDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
    })
    @GetMapping
    public ResponseEntity<?> doc2(@PathVariable("checkListId") Long checkListId) {

        List<CustomCheckListResponseDto> customCheckListResponseDtos = Arrays.asList(new CustomCheckListResponseDto());
        return succes(customCheckListResponseDtos, GET_CUSTOM_ALL_QUESTION);
    }

    @ApiOperation(value = "3-2. 카테고리,질문,체크여부 한번에 받아서 저장", notes = "카테고리 하위 항목들을 id를 따져가면서 받기에 힘들 수도 있을 듯 -> 그냥 다 새로 저장할까...")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PostMapping
    public ResponseEntity<?> doc(@PathVariable("checkListId") Long checkListId, @RequestBody CustomRequestDto dto) {

        return succes(CREATED, CREATE_CUSTOM_QUESTION);
    }

    @ApiOperation(value = "3-3. 카테고리, 질문, 체크여부 한번에 받아서 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PutMapping
    public ResponseEntity<?> doc2(@PathVariable("checkListId") Long checkListId, @RequestBody CustomRequestDto dto) {

        return succes(CREATED, UPDATE_CUSTOM_QUESTION);
    }


    @ApiOperation(value = "3-4. 카테고리 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
    })
    @DeleteMapping
    public ResponseEntity<?> doc11(@PathVariable("checkListId") Long checkListId) {

        return succes(CREATED, DELETE_CUSTOM_CATEGORY);
    }

    @ApiOperation(value = "3-5. 카테고리의 상세 항목 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "커스텀 카테고리의 하위 항목 아이디 리스트", example = "3", required = true, paramType = "params")
    })
    @DeleteMapping("/question")
    public ResponseEntity<?> doc4(@PathVariable("checkListId") Long checkListId, @RequestParam("id") List<Long> id) {

        return succes(OK, DELETE_CUSTOM_QUESTION);
    }

}
