package com.example.amattang.controller;

import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.request.CommonQuestionReqeustDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<CommonCheckListDto> doc2(@PathVariable("checkListId") Long checkListId, @RequestParam("mainCategory") String mainCategory,
                     @RequestParam(value = "subCategory", required = false) String subCategory, @RequestParam(value = "visibility") Boolean visibility) {
        return null;
    }

    @ApiOperation(value = "2-2. 답변 등록하기 (미완성)", notes = "타입을 어떻게 나눠서 요청받을 수 있을까")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CommonQuestionReqeustDto", value = "카테고리 아이디와 답변 목록")
    })
    @PutMapping("/question")
    public void doc5(@PathVariable("checkListId") Long checkListId, @RequestBody CommonQuestionReqeustDto dto) {

    }

    @ApiOperation(value = "2-3.삭제했던 질문 다시 추가하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "questionIds", value = "추가할 질문 아이디 목록", paramType = "params")
    })
    @GetMapping("/question")
    public void doc4(@PathVariable("checkListId") Long checkListId, @RequestParam("questionIds") List<Long> id) {

    }


    @ApiOperation(value = "2-4.질문 삭제하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "questionIds", value = "삭제할 질문 아이디 목록", paramType = "params")
    })
    @DeleteMapping("/question")
    public void doc3(@PathVariable("checkListId") Long checkListId, @RequestParam("questionIds") List<Long> ids) {

    }


}
