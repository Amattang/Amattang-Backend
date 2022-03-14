package com.example.amattang.service;

import com.example.amattang.domain.answer.*;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.listToQuestion.ListToQuestionRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.payload.request.CommonRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.NOT_ACCESS_USER;

@Service
@RequiredArgsConstructor
public class CommonQuestionAnswerService {

    private final CheckListRepository checkListRepository;
    private final ListToQuestionRepository relationRepository;
    private final AnswerRepository answerRepository;
    private final QuestionToAnswerRepository questionToAnswerRepository;

    @Transactional
    public void updateAnswer(User user, Long checkListId, CommonRequestDto dto) {
        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 체크리스트입니다."));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());

        for (CommonRequestDto.TypeARequest a : dto.getTypeA()) {
            ListToQuestion listToQuestion = relationRepository.findByCheckListId_IdAndCommonQuestionId_Id(checkListId, a.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException());
            if (listToQuestion.getQuestionToAnswer() != null) {
                questionToAnswerRepository.deleteById(listToQuestion.getQuestionToAnswer().getId());
            }

            QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);

            List<AnswerBool> answer = a.getAnswer().stream()
                    .map(x -> new AnswerBool(x.getType(),x.getRedType(), questionToAnswer,x.getAns()))
                    .collect(Collectors.toList());

            questionToAnswer.setAnswerBoolList(answer);
            listToQuestion.setQuestionToAnswer(questionToAnswer);

        }

        for (CommonRequestDto.TypeBRequest b : dto.getTypeB()) {

            ListToQuestion listToQuestion = relationRepository.findByCheckListId_IdAndCommonQuestionId_Id(checkListId, b.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException());
            if (listToQuestion.getQuestionToAnswer() != null) {
                questionToAnswerRepository.deleteById(listToQuestion.getQuestionToAnswer().getId());
            }

            QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);
            List<AnswerString> answer = b.getAnswer().stream()
                    .map(x -> new AnswerString(x.getDescription(), questionToAnswer, x.getType()))
                    .collect(Collectors.toList());
            questionToAnswer.setAnswerStringList(answer);
            listToQuestion.setQuestionToAnswer(questionToAnswer);
        }

        for (CommonRequestDto.TypeDRequest d : dto.getTypeD()) {
            ListToQuestion listToQuestion = relationRepository.findByCheckListId_IdAndCommonQuestionId_Id(checkListId, d.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException());
            if (listToQuestion.getQuestionToAnswer() != null) {
                questionToAnswerRepository.deleteById(listToQuestion.getQuestionToAnswer().getId());
            }

            QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);
            List<AnswerBool> answer = d.getAnswer().stream()
                    .map(x -> new AnswerBool(x.getType(), false, questionToAnswer, x.getAns()))
                    .collect(Collectors.toList());

            questionToAnswer.setAnswerBoolList(answer);
            listToQuestion.setQuestionToAnswer(questionToAnswer);

        }
    }


    public void isCorrectAuthor(String userId, String authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAccessUserException(NOT_ACCESS_USER);
        }
    }

}
