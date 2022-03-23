package com.example.amattang.controller;


import com.example.amattang.controller.ValidationGroup.generalGroup;
import com.example.amattang.controller.ValidationGroup.saveGroup;
import com.example.amattang.controller.ValidationGroup.updateGroup;
import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.CustomCheckListResponseDto;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.payload.request.CustomRequestDto;
import com.example.amattang.security.CurrentUser;
import com.example.amattang.security.UserPrincipal;
import com.example.amattang.service.CustomQuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static com.example.amattang.payload.reponse.ResponseUtil.success;
import static org.springframework.http.HttpStatus.CREATED;

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
    public ResponseEntity<?> getAllCustomLists(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        List<CustomCheckListResponseDto> customQuestionList = questionService.getAllCategoryAndAnswer(user, checkListId);
        return ResponseUtil.success(customQuestionList, GET_CUSTOM_ALL_QUESTION);
    }

    @ApiOperation(value = "3-2. 카테고리,질문,체크여부 한번에 받아서 저장", notes = "카테고리 하위 항목들을 id를 따져가면서 받기에 힘들 수도 있을 듯 -> 그냥 다 새로 저장할까...")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PostMapping
    public ResponseEntity<?> saveCustomList(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestBody @Validated({saveGroup.class, generalGroup.class}) CustomRequestDto dto) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.saveCategoryAndAnswer(user, checkListId, dto);
        return ResponseUtil.success(CREATED, CREATE_CUSTOM_QUESTION);
    }

    @ApiOperation(value = "3-3. 카테고리, 질문, 체크여부 한번에 받아서 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "CustomQuestionRequestDto")
    })
    @PutMapping
    public ResponseEntity<?> updateCustomList(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestBody @Validated({updateGroup.class, generalGroup.class}) CustomRequestDto dto) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.updateCategoryAndAnswer(user, checkListId, dto);
        return ResponseUtil.success(CREATED, UPDATE_CUSTOM_QUESTION);
    }


    @ApiOperation(value = "3-4. 카테고리 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "categoryId", value = "삭제할 카테고리 아이디", paramType = "param"),
    })
    @DeleteMapping
    public ResponseEntity<?> deleteCustomCategory(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestParam("categoryId") List<Long> categoryIds) {
        if (categoryIds.size() == 0) throw new IllegalArgumentException("카테고리 아이디 리스트는 최소 1개 이상의 아이디를 포함해야 합니다.");
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        questionService.deleteCategories(user, checkListId, categoryIds);
        return ResponseUtil.success(CREATED, DELETE_CUSTOM_CATEGORY);
    }

}
