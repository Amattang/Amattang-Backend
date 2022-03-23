package com.example.amattang.controller;

import com.example.amattang.domain.commonQuestion.CommonQuestion.MAIN_CATEGORY;
import com.example.amattang.domain.commonQuestion.CommonQuestion.SUB_CATEGORY;
import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.payload.request.CommonRequestDto;
import com.example.amattang.payload.request.CommonVisibilityRequestDto;
import com.example.amattang.payload.request.CommonVisibilityRequestDto.Question;
import com.example.amattang.security.CurrentUser;
import com.example.amattang.security.UserPrincipal;
import com.example.amattang.service.CheckListService;
import com.example.amattang.service.CommonQuestionAnswerService;
import com.example.amattang.service.CommonQuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static com.example.amattang.payload.reponse.ResponseUtil.success;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/check-list")
public class CommonQuestionController {

    private final CommonQuestionAnswerService answerService;
    private final CommonQuestionService questionService;
    private final CheckListService checkListService;
    private final UserRepository userRepository;


    //리스트가 없는 경우
    @ApiOperation(value = "2-1. 체크리스트 생성 및 질문 목록 반환", response = CommonCheckListDto.class)
    @GetMapping("/init")
    public ResponseEntity<?> createCheckList(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        Map<String, Long> checkList = checkListService.createCheckList(user);
        return success(CREATED, checkList, CREATE_CHECK_LIST);
    }

    //리스트가 있는 경우
    @ApiOperation(value = "2-2.(세부)카테고리 별로 목록 반환", notes = "세부 카테고리가 없는데, 세부 카테고리를 명시했을 경우 무시한다.", response = CommonQuestionDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name="mainCategory", value = "체크리스트의 1차 카테고리",paramType = "param", required = true),
            @ApiImplicitParam(name="subCategory", value = "체크리스트의 2차 카테고리", paramType = "param")
    })
    @GetMapping("/{checkListId}/common")
    public ResponseEntity<?> getCommonQuestionListByCategory(@PathVariable("checkListId") Long checkListId,
                                                             @RequestParam("mainCategory") String mainCategory,
                                                             @RequestParam(value = "subCategory", required = false) String subCategory) {
        MAIN_CATEGORY main = MAIN_CATEGORY.fromMsg(mainCategory).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메인 카테고리입니다."));
        if (main.equals(MAIN_CATEGORY.INSIDE) && subCategory == null) {
            throw new IllegalArgumentException("서브 카테고리를 입력해주세요");
        }
        SUB_CATEGORY sub = SUB_CATEGORY.NONE;
        if (subCategory != null) {
            sub = SUB_CATEGORY.fromMsg(subCategory).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 서브 카테고리입니다."));
        }
        CommonCheckListDto commonCheckListDto = questionService.getCheckListQuestionsWithAnswer(checkListId, main.getMsg(), sub.getMsg());
        return ResponseUtil.success(commonCheckListDto, GET_CHECK_LIST_CATEGORY);
    }

    @ApiOperation(value = "2-3. 답변 등록하기", notes = "타입을 어떻게 나눠서 요청받을 수 있을까")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path")
    })
    @PutMapping("/{checkListId}/common/question")
    public ResponseEntity<?> saveAnswerToCommonQuestion(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestBody @Valid CommonRequestDto dto) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        answerService.updateAnswer(user, checkListId, dto);
        return ResponseUtil.success(CREATED, UPDATE_CHECK_LIST_ANSWER);
    }

    @ApiOperation(value = "2-4.삭제했던 질문 다시 추가하기 / 질문 삭제하기 (질문 상태 변경)", response = ResponseUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "체크리스트 아이디", paramType = "path")
    })
    @PutMapping("/{checkListId}/common/question/status")
    public ResponseEntity<?> changeStatusCommonQuestion(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") @Valid Long checkListId, @RequestBody @Valid CommonVisibilityRequestDto dto) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        for (Question d : dto.getQuestion()) {
            if (d.getQuestionId() == null) {
                throw new IllegalArgumentException("삭제(추가)할 질문 아이디가 누락되었습니다.");
            }
            if (d.getVisibility() == null) {
                throw new IllegalArgumentException("질문 삭제 여부가 누락되었습니다.");
            }
        }

        questionService.updateCommonQuestionStatus(user, checkListId, dto);
        return ResponseUtil.success(CREATED, UPDATE_CHECK_LIST_QUESTION_STATUS);
    }

}
