package com.example.amattang.controller;


import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.CustomCheckListResponseDto;
import com.example.amattang.payload.request.CustomRequestDto;
import com.example.amattang.security.CurrentUser;
import com.example.amattang.security.UserPrincipal;
import com.example.amattang.service.CustomQuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static com.example.amattang.payload.reponse.ResponseUtil.succes;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/check-list/{checkListId}/custom")
public class CustomQuestionController {

    private final UserRepository userRepository;
    private final CustomQuestionService questionService;

    @ApiOperation(value = "3-1. 전체 카테고리와 하위 항목 반환", response = CustomCheckListResponseDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
    })
    @GetMapping
    public ResponseEntity<?> doc2(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        List<CustomCheckListResponseDto> customQuestionList = questionService.getAllCategoryAndAnswer(user, checkListId);
        return succes(customQuestionList, GET_CUSTOM_ALL_QUESTION);
    }

    @ApiOperation(value = "3-2. 카테고리,질문,체크여부 한번에 받아서 저장", notes = "카테고리 하위 항목들을 id를 따져가면서 받기에 힘들 수도 있을 듯 -> 그냥 다 새로 저장할까...")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PostMapping
    public ResponseEntity<?> doc(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestBody CustomRequestDto dto) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.saveCategoryAndAnswer(user, checkListId, dto);
        return succes(CREATED, CREATE_CUSTOM_QUESTION);
    }

    @ApiOperation(value = "3-3. 카테고리, 질문, 체크여부 한번에 받아서 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PutMapping
    public ResponseEntity<?> doc2(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestBody CustomRequestDto dto) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.updateCategoryAndAnswer(user, checkListId, dto);
        return succes(CREATED, UPDATE_CUSTOM_QUESTION);
    }


    @ApiOperation(value = "3-4. 카테고리 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "categoryId", value = "삭제할 카테고리 아이디", paramType = "param"),
    })
    @DeleteMapping
    public ResponseEntity<?> doc11(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestParam("categoryId") List<Long> categoryIds) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.deleteCategories(user, checkListId, categoryIds);
        return succes(CREATED, DELETE_CUSTOM_CATEGORY);
    }

    @ApiOperation(value = "3-5. 카테고리의 상세 항목 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "커스텀 카테고리의 하위 항목 아이디 리스트", example = "3", required = true, paramType = "params")
    })
    @DeleteMapping("/question/{categoryId}")
    public ResponseEntity<?> doc4(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId,
                                  @PathVariable("categoryId") Long categoryId, @RequestParam("id") List<Long> id) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.deleteCategoryItems(user, checkListId, categoryId, id);
        return succes(OK, DELETE_CUSTOM_QUESTION);
    }

}
