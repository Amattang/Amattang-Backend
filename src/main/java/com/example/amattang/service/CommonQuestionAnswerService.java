package com.example.amattang.service;

import com.example.amattang.domain.answer.AnswerBool;
import com.example.amattang.domain.answer.AnswerString;
import com.example.amattang.domain.answer.QuestionToAnswer;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.listToQuestion.ListToQuestionRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.payload.request.CommonRequestDto;
import com.example.amattang.payload.request.CommonRequestDto.TypeARequest;
import com.example.amattang.payload.request.CommonRequestDto.TypeARequest.ButtonRequest;
import com.example.amattang.payload.request.CommonRequestDto.TypeBRequest;
import com.example.amattang.payload.request.CommonRequestDto.TypeBRequest.AnswerBRequest;
import com.example.amattang.payload.request.CommonRequestDto.TypeDRequest;
import com.example.amattang.payload.request.CommonRequestDto.TypeDRequest.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.NOT_ACCESS_USER;
import static com.example.amattang.exception.ExceptionMessage.OVERLAP_QUESTION_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonQuestionAnswerService {

    private final CheckListRepository checkListRepository;
    private final ListToQuestionRepository relationRepository;

    @Transactional
    public void updateAnswer(User user, Long checkListId, CommonRequestDto dto) {

        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 체크리스트입니다."));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());

        //type 별로 정리하기

        saveAnswerTypeA(dto.getTypeA(), checkList);
        saveAnswerTypeB(dto.getTypeB(), checkList);
        saveAnswerTypeD(dto.getTypeD(), checkList);

        if (dto.getTypeM() != null) {
            checkList.setAddress(dto.getTypeM().getAddress());
            checkList.setLatitude(dto.getTypeM().getLatitude());
            checkList.setLongitude(dto.getTypeM().getLongitude());
        }

        //update to check-list
    }

    public void saveAnswerTypeA(List<TypeARequest> list, CheckList checkList) {

        Collections.sort(list, new Comparator<TypeARequest>() {
            @Override
            public int compare(TypeARequest o1, TypeARequest o2) {
                int compare = o1.getQuestionId().compareTo(o2.getQuestionId());
                if (compare == 0) throw new IllegalArgumentException(OVERLAP_QUESTION_ID + o1.getQuestionId());
                return compare;
            }
        });
        for (TypeARequest a : list) {

            ListToQuestion listToQuestion = initAnswer(checkList.getId(), a.getQuestionId());
            QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);

            List<AnswerBool> answer = a.getAnswer().stream()
                    .map(x -> new AnswerBool(x.getType(),x.getRedType(), questionToAnswer,x.getVal()))
                    .collect(Collectors.toList());

            questionToAnswer.setAnswerBoolList(answer);
            listToQuestion.setQuestionToAnswer(questionToAnswer);

            if (StringUtils.hasText(listToQuestion.getCommonQuestionId().getBasicType())) {
                updateBasicInfoToCheckList(a, listToQuestion, checkList);
            }

        }
    }

    public void saveAnswerTypeB(List<TypeBRequest> list, CheckList checkList) {

        Collections.sort(list, new Comparator<TypeBRequest>() {
            @Override
            public int compare(TypeBRequest o1, TypeBRequest o2) {
                int compare = o1.getQuestionId().compareTo(o2.getQuestionId());
                if (compare == 0) throw new IllegalArgumentException(OVERLAP_QUESTION_ID  + o1.getQuestionId());
                return compare;
            }
        });

        for (TypeBRequest b : list) {

            ListToQuestion listToQuestion = initAnswer(checkList.getId(), b.getQuestionId());

            QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);
            List<AnswerString> answer = b.getAnswer().stream()
                    .map(x -> new AnswerString(x.getDescription(), questionToAnswer, x.getType()))
                    .collect(Collectors.toList());

            questionToAnswer.setAnswerStringList(answer);
            listToQuestion.setQuestionToAnswer(questionToAnswer);

            if (StringUtils.hasText(listToQuestion.getCommonQuestionId().getBasicType())) {
                updateBasicInfoToCheckList(b, listToQuestion, checkList);
            }
        }
    }
    public void saveAnswerTypeD(List<TypeDRequest> list, CheckList checkList) {

        Collections.sort(list, new Comparator<TypeDRequest>() {
            @Override
            public int compare(TypeDRequest o1, TypeDRequest o2) {
                int compare = o1.getQuestionId().compareTo(o2.getQuestionId());
                if (compare == 0) throw new IllegalArgumentException(OVERLAP_QUESTION_ID + o1.getQuestionId());
                return compare;
            }
        });

        for (TypeDRequest d : list) {

            ListToQuestion listToQuestion = initAnswer(checkList.getId(), d.getQuestionId());
            QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);

            List<AnswerBool> answer = d.getAnswer().stream()
                    .map(x -> new AnswerBool(x.getType(), false, questionToAnswer, x.getVal()))
                    .collect(Collectors.toList());

            questionToAnswer.setAnswerBoolList(answer);
            listToQuestion.setQuestionToAnswer(questionToAnswer);

            if (StringUtils.hasText(listToQuestion.getCommonQuestionId().getBasicType())) {
                updateBasicInfoToCheckList(d, listToQuestion, checkList);
            }
        }
    }

    public ListToQuestion initAnswer(Long checkListId, Long id) {
        ListToQuestion listToQuestion = relationRepository.findByCheckListId_IdAndCommonQuestionId_Id(checkListId, id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문 아이디입니다. id => " + id));
        listToQuestion.deleteAnswer();

        return listToQuestion;
    }

    public String updateBasicInfoToCheckList(TypeARequest request, ListToQuestion relation, CheckList checkList) {
        String basic = relation.getCommonQuestionId().getBasicType();
        List<String> list = new ArrayList<>();
        for (ButtonRequest r : request.getAnswer()) {
            if (r.getVal()) {
                list.add(r.getType());
            }
        }
        return setValueToCheckList(checkList, list, basic);
    }
    public String updateBasicInfoToCheckList(TypeBRequest request, ListToQuestion relation, CheckList checkList) {
        String basic = relation.getCommonQuestionId().getBasicType();
        List<String> list = new ArrayList<>();
        for (AnswerBRequest r : request.getAnswer()) {
            list.add(r.getType());
            list.add(r.getDescription());
        }
        return setValueToCheckList(checkList, list, basic);

    }
    public String updateBasicInfoToCheckList(TypeDRequest request, ListToQuestion relation, CheckList checkList) {
        String basic = relation.getCommonQuestionId().getBasicType();
        List<String> list = new ArrayList<>();
        for (Request r : request.getAnswer()) {
            if (r.getVal()) {
                list.add(r.getType());
                break;
            }
        }
        return setValueToCheckList(checkList, list, basic);
    }

    public String setValueToCheckList(CheckList checkList, List<String> list, String basicType) {
        log.debug("data : " + list.toString().replaceAll("[\\[\\]]", ""));
        switch (basicType) {
            case "title":
                checkList.setTitle(list.get(0));
                break;
            case "type":
                checkList.setRoomType(list.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", ""));
                break;
            case "floor":
                checkList.setFloor(list.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", ""));
                break;
            case "distance":
                if (list.size() == 2) checkList.setDistance(list.get(0) + list.get(1));
                else if(list.size() == 4) checkList.setDistance(list.get(0) + " " + list.get(2) + list.get(3));
                break;
            case "area":
                checkList.setArea(list.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", ""));
                break;
            default: break;
        }
        return null;
    }


    public void isCorrectAuthor(String userId, String authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAccessUserException(NOT_ACCESS_USER);
        }
    }

}
