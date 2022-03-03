package com.example.amattang.controller;

import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.payload.request.CommonReqeustDto;
import com.example.amattang.payload.request.CommonVisibilityRequestDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/check-list/{checkListId}/common")
public class CommonQuestionController {

    @ApiOperation(value = "2-1.(세부)카테고리 별로 목록 반환", notes = "타입을 어떻게 나눠서 전송할 수 있을까", response = CommonCheckListDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name="mainCategory", value = "체크리스트의 1차 카테고리",paramType = "param", required = true),
            @ApiImplicitParam(name="subCategory", value = "체크리스트의 2차 카테고리", paramType = "param"),
            @ApiImplicitParam(name="visibility", value = "체크리스트에서의 질문 삭제 여부", paramType = "param", required = true),
    })
    @GetMapping
    public ResponseEntity<?> doc2(@PathVariable("checkListId") Long checkListId, @RequestParam("mainCategory") String mainCategory,
                               @RequestParam(value = "subCategory", required = false) String subCategory, @RequestParam(value = "visibility") Boolean visibility) {

        List<CommonCheckListDto> commonCheckListDtos = Arrays.asList(new CommonCheckListDto());
        return ResponseUtil.succes(commonCheckListDtos, GET_CHECK_LIST_CATEGORY);
    }

    @ApiOperation(value = "2-2. 답변 등록하기 (미완성)", notes = "타입을 어떻게 나눠서 요청받을 수 있을까")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CommonQuestionReqeustDto", value = "카테고리 아이디와 답변 목록")
    })
    @PutMapping("/question")
    public ResponseEntity<?> doc5(@PathVariable("checkListId") Long checkListId, @RequestBody CommonReqeustDto dto) {

        return ResponseUtil.succes(CREATED, UPDATE_CHECK_LIST_ANSWER);
    }

    @ApiOperation(value = "2-3.삭제했던 질문 다시 추가하기 / 질문 삭제하기 (질문 상태 변경)", response = ResponseUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "questionIds", value = "(삭제(추가)할 질문 아이디, 삭제 여부) 리스트")
    })
    @PutMapping("/question/status")
    public ResponseEntity<?> doc4(@PathVariable("checkListId") Long checkListId, @RequestBody List<CommonVisibilityRequestDto> dto) {

        return ResponseUtil.succes(CREATED, UPDATE_CHECK_LIST_QUESTION_STATUS);
    }

}
