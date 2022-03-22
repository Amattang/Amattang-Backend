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

import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.NOT_ACCESS_USER;

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
        String data = "";
        String data2 = "";
        for (ButtonRequest r : request.getAnswer()) {
            if (r.getVal()) {
                data = r.getType();
            }
        }
        return setValueToCheckList(checkList, data, data2, basic);
    }
    public String updateBasicInfoToCheckList(TypeBRequest request, ListToQuestion relation, CheckList checkList) {
        String basic = relation.getCommonQuestionId().getBasicType();
        String data = "";
        String data2 = "";
        for (AnswerBRequest r : request.getAnswer()) {
            data = r.getType();
            data2 = r.getDescription();
        }
        return setValueToCheckList(checkList, data, data2, basic);

    }
    public String updateBasicInfoToCheckList(TypeDRequest request, ListToQuestion relation, CheckList checkList) {
        String basic = relation.getCommonQuestionId().getBasicType();
        String data = "";
        String data2 = "";
        for (Request r : request.getAnswer()) {
            if (r.getVal()) {
                data = r.getType();
                break;
            }
        }
        return setValueToCheckList(checkList, data, data2, basic);
    }

    public String setValueToCheckList(CheckList checkList, String data, String data2, String basicType) {
        switch (basicType) {
            case "title":
                checkList.setTitle(data);
                break;
            case "type":
                checkList.setRoomType(data + data2);
                break;
            case "floor":
                checkList.setFloor(data + data2);
                break;
            case "distance":
                checkList.setDistance(data + data2);
                break;
            case "area":
                checkList.setArea(data + data2);
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
