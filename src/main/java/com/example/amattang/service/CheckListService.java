package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CommonQuestionRepository questionRepository;
    private final CheckListRepository checkListRepository;
    private final CommonQuestionService questionService;

    //체크리스트 생성
    public CommonCheckListDto createCheckList(User user) {
        CheckList checkList = new CheckList(user, false);
        List<CommonQuestion> question = questionRepository.findAll();

        Set<ListToQuestion> relationSet = question.stream()
                .map(x -> ListToQuestion.builder()
                        .commonQuestionId(x)
                        .checkListId(checkList)
                        .visibility(false)
                        .build()
                )
                .collect(Collectors.toSet());

        checkList.setListCommonQuestion(relationSet);
        checkListRepository.save(checkList);
        //체크리스트 외부 시설만 뽑아서 반환
        List<CommonQuestionDto> questionDtos = questionService.getQuestionByCategory("외부시설");
        return CommonCheckListDto.create(checkList, questionDtos);
    }

}
