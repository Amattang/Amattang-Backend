package com.example.amattang.service;

import com.example.amattang.domain.answer.*;
import com.example.amattang.domain.answer.dto.*;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.*;
import com.example.amattang.domain.image.Image;
import com.example.amattang.domain.image.ImageRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.payload.reponse.CommonCheckListDto;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import com.example.amattang.payload.request.CommonVisibilityRequestDto;
import com.example.amattang.payload.request.CommonVisibilityRequestDto.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
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
    private final ImageRepository imageRepository;

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
                .map(x -> (!x.getCommonQuestionId().getAnsType().equals("C") && x.getQuestionToAnswer() == null) ?
                        mapToDto(x.getCommonQuestionId()) :
                        mapToDtoWithAnswer(x.getCommonQuestionId(), x.getQuestionToAnswer(), checkList)
                )
                .collect(Collectors.toList());

        return CommonCheckListDto.create(checkList, list1);
    }

    public CommonQuestionDto mapToDtoWithAnswer(CommonQuestion question, QuestionToAnswer questionToAnswer, CheckList checkList) {
        List answertDto = mapFromQuestionToTypeAnswer(question, questionToAnswer, checkList);
        return CommonQuestionDto.fromQuestion(question, answertDto);
    }

    public CommonQuestionDto mapToDto(CommonQuestion question) {
        List answer = mapFromQuestionToTypeAnswer(question);
        return CommonQuestionDto.fromQuestion(question, answer);
    }

    public List<? extends Object> mapFromQuestionToTypeAnswer(CommonQuestion question) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromQuestion(entityManager.find(CommonQuestionTypeA.class, question.getId()));
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromQuestion(entityManager.find(CommonQuestionTypeB.class, question.getId()));
        } else if (question.getAnsType().equals("C")) {
            //이미지 타입으로 변경
            return null;
        } else if (question.getAnsType().equals("D")) {
            return AnswerDDto.fromQuestion(entityManager.find(CommonQuestionTypeD.class, question.getId()));
        } else {
            return null;
        }
    }
    public List<? extends Object> mapFromQuestionToTypeAnswer(CommonQuestion question, QuestionToAnswer questionToAnswer, CheckList checkList) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromAnswer(entityManager.find(CommonQuestionTypeA.class, question.getId()), questionToAnswer.getAnswerList(), questionToAnswer.getId());
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromAnswer(entityManager.find(CommonQuestionTypeB.class, question.getId()), questionToAnswer.getAnswerList(), questionToAnswer.getId());
        } else if (question.getAnsType().equals("C")) {
            Optional<List<Image>> optionalImages = imageRepository.findAllByCheckListId_Id(checkList.getId());

            if (optionalImages.isEmpty()) {
                return null;
            }

            List<Image> imageList = optionalImages.get();
            return imageList.stream()
                    .map(x -> new AnswerCDto(x.getId(), x.getUrl()))
                    .collect(Collectors.toList());
        } else {
            return AnswerDDto.fromAnswer(entityManager.find(CommonQuestionTypeD.class, question.getId()), questionToAnswer.getAnswerList(), questionToAnswer.getId());
        }
    }


    @Transactional
    public void updateCommonQuestionStatus(User user, Long id, CommonVisibilityRequestDto dto) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        List<ListToQuestion> listCommonQuestion = checkList.getListCommonQuestion();
        int i = 0;
        for (ListToQuestion q : listCommonQuestion) {
            if (i == dto.getQuestion().size()) break;

            Question question = dto.getQuestion().get(i);

            if (q.getCommonQuestionId().getId() == question.getId()) {
                q.setVisibility(question.getVisibility());
                if (!question.getVisibility()) {
                    q.deleteAnswer();
                }
                i++;
            }
        }
    }

    public void isCorrectAuthor(String userId, String authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAccessUserException(NOT_ACCESS_USER);
        }
    }
}
