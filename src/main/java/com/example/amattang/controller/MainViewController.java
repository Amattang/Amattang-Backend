package com.example.amattang.controller;

import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.MainCheckListResponseDto;
import com.example.amattang.payload.reponse.MainViewCheckListResponseDto;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.security.CurrentUser;
import com.example.amattang.security.UserPrincipal;
import com.example.amattang.service.CheckListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.amattang.payload.reponse.ResponseMessage.GET_USER_ALL_CHECK_LIST;
import static com.example.amattang.payload.reponse.ResponseMessage.UPDATE_PINNED_CHECK_LIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/check-list")
public class MainViewController {

    private final CheckListService checkListService;
    private final UserRepository userRepository;

    @ApiOperation(value = "1-1.체크리스트 목록(기본정보) 반환", response = MainCheckListResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message="체크리스트의 아이디, 기본정보를 반환", response = MainCheckListResponseDto.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity<?> getAllCheckList(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        List<MainViewCheckListResponseDto> allCheckList = checkListService.getAllCheckListWithAnswer(user);
        return ResponseUtil.succes(allCheckList, GET_USER_ALL_CHECK_LIST);
    }

    @ApiOperation(value = "1-2.체크리스트 핀 여부 수정", response = MainCheckListResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message="체크리스트 핀 여부 변경 성공")
    })
    @GetMapping("/main")
    public ResponseEntity<?> setCheckListPinned(@CurrentUser UserPrincipal userPrincipal, @RequestParam("checkListId") List<Long> checkListIds) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        checkListService.changeCheckListIsPinned(user, checkListIds);
        return ResponseUtil.succes(UPDATE_PINNED_CHECK_LIST, HttpStatus.CREATED);
    }
}
