package com.example.amattang.service;

import com.example.amattang.domain.answer.*;
import com.example.amattang.domain.answer.dto.AnswerADto;
import com.example.amattang.domain.answer.dto.AnswerBDto;
import com.example.amattang.domain.answer.dto.AnswerCDto;
import com.example.amattang.domain.answer.dto.AnswerDDto;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.*;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import com.example.amattang.payload.request.CommonVisibilityRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.NOT_ACCESS_USER;
import static com.example.amattang.exception.ExceptionMessage.NOT_EXIST_CHECK_LIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonQuestionService {
    private final CommonQuestionRepository questionRepository;
    private final CheckListRepository checkListRepository;
    private final EntityManager entityManager;

    public List<CommonQuestionDto> getQuestionByCategory(String mainCategory) {
        List<CommonQuestion> questionList = questionRepository.findByMainCategory(mainCategory, Sort.by(Sort.Direction.ASC, "id"));
        List<CommonQuestionDto> checkListDtoList = questionList.stream()
                .filter(x -> x.getMainCategory().equals(mainCategory))
                .map(x -> mapToDto(x))
                .collect(Collectors.toList());
        return checkListDtoList;
    }


    public CommonCheckListDto getCheckListQuestionsWithAnswer(User user, Long id, String main, String subCategory, Boolean visibility) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());

        List<ListToQuestion> listCommonQuestion = checkList.getListCommonQuestion();

        List<CommonQuestionDto> list1 = listCommonQuestion.stream()
                .filter(w -> (subCategory == null) ?
                        w.getCommonQuestionId().getMainCategory().equals(main) && w.getVisibility().equals(visibility):
                                w.getCommonQuestionId().getMainCategory().equals(main) && w.getCommonQuestionId().getSubCategory().equals(subCategory) && w.getVisibility().equals(visibility)
                        )
                .map(x -> x.getAnswer() == null ?
                        mapToDto(x.getCommonQuestionId()) :
                        mapToDtoWithAnswer(x.getCommonQuestionId(), x.getAnswer())
                )
                .collect(Collectors.toList());

        return CommonCheckListDto.create(checkList, list1);
    }

    public CommonQuestionDto mapToDtoWithAnswer(CommonQuestion question, Answer answer) {
        Object answertDto = mapFromQuestionToTypeAnswer(question, answer);
        return CommonQuestionDto.fromQuestion(question, answertDto);
    }

    public CommonQuestionDto mapToDto(CommonQuestion question) {
        Object answer = mapFromQuestionToTypeAnswer(question);
        return CommonQuestionDto.fromQuestion(question, answer);
    }

    public Object mapFromQuestionToTypeAnswer(CommonQuestion question, Answer answer) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromAnswer(entityManager.find(CommonQuestionTypeA.class, question.getId()), (AnswerA) answer, answer.getId());
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromAnswer(entityManager.find(CommonQuestionTypeB.class, question.getId()), (AnswerB) answer, answer.getId());
        } else if (question.getAnsType().equals("C")) {
            return AnswerCDto.fromAnswer(entityManager.find(CommonQuestionTypeC.class, question.getId()), (AnswerC) answer, answer.getId());
        } else {
            return AnswerDDto.fromAnswer(entityManager.find(CommonQuestionTypeD.class, question.getId()), (AnswerD) answer, answer.getId());
        }
    }


    public Object mapFromQuestionToTypeAnswer(CommonQuestion question) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromQuestion(entityManager.find(CommonQuestionTypeA.class, question.getId()));
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromQuestion(entityManager.find(CommonQuestionTypeB.class, question.getId()));
        } else if (question.getAnsType().equals("C")) {
            return AnswerCDto.fromQuestion(entityManager.find(CommonQuestionTypeC.class, question.getId()));
        } else {
            return AnswerDDto.fromQuestion(entityManager.find(CommonQuestionTypeD.class, question.getId()));
        }
    }


    public void isCorrectAuthor(String userId, String authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAccessUserException(NOT_ACCESS_USER);
        }
    }
}
