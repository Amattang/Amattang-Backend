package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import com.example.amattang.payload.reponse.MainViewCheckListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.domain.commonQuestion.CommonQuestion.MAIN_CATEGORY.BASIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CommonQuestionRepository questionRepository;
    private final CheckListRepository checkListRepository;
    private final CommonQuestionService questionService;

    //체크리스트 반환, 답변이 없는 체크리스트 제외
    public List<MainViewCheckListResponseDto> getAllCheckListWithAnswer(User user) {
        List<CheckList> checkLists = user.getCheckLists().stream()
                .filter(x -> x.isGetAnswer() == true)
                .collect(Collectors.toList());
        Collections.sort(checkLists, (a,b) -> (a.getId().compareTo(b.getId())) * (-1));
        List<MainViewCheckListResponseDto> list = checkLists.stream()
                .map(x -> MainViewCheckListResponseDto.fromEntity(x))
                .collect(Collectors.toList());
        if (list.size() > 0) {
            list.get(0).setCenter(true);
        }
        return list;
    }

    @Transactional
    public void changeCheckListIsPinned(User user, List<Long> checkListIds) {
        List<CheckList> checkLists = user.getCheckLists();

        checkLists.stream()
                .forEach(x -> x.setPinned(false));

        for (Long id : checkListIds) {
            checkLists.stream()
                    .filter(x -> x.getId() == id && x.isGetAnswer())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("현재 사용자에 존재하지 않는 체크리스트입니다. id => " + id))
                    .setPinned(true);
        }

    }

    public CommonCheckListDto createCheckList(User user) {
        CheckList checkList = new CheckList(user, false);
        List<CommonQuestion> question = questionRepository.findAll();

        List<ListToQuestion> relationSet = question.stream()
                .map(x -> ListToQuestion.builder()
                        .commonQuestionId(x)
                        .checkListId(checkList)
                        .visibility(true)
                        .build()
                )
                .collect(Collectors.toList());

        checkList.setListCommonQuestion(relationSet);
        checkListRepository.save(checkList);
        //체크리스트 기본정보만
        List<CommonQuestionDto> questionDtos = questionService.getQuestionByCategory(BASIC.getMsg());
        return CommonCheckListDto.create(checkList, questionDtos);
    }

}
