package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestionRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.payload.reponse.MainViewCheckListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainViewService {

    private final CheckListRepository checkListRepository;
    private final ListToQuestionRepository relationRepository;

    //체크리스트 반환, 답변이 없는 체크리스트 제외
    public MainViewCheckListResponseDto getAllCheckListWithAnswer(User user) {

        //체크리스트에서 답변 중 기본정보만 추출
        //기본정보에서 basic type을 확인 -> 답변 있는지 확인 -> 답변 넣음

        log.warn("hello");
        Set<CheckList> checkListSet = user.getCheckListSet();
        log.warn("hello22");
        List<MainViewCheckListResponseDto.MainViewCheckListDto> collect = checkListSet.stream()
                .map(x -> MainViewCheckListResponseDto.MainViewCheckListDto.fromEntity(relationRepository.findCheckListMainInformation(x.getId()), x.getId(), null))
                .collect(Collectors.toList());
        return MainViewCheckListResponseDto.fromEntity(collect);

    }
}
