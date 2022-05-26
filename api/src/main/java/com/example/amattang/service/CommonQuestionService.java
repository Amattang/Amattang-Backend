package com.example.amattang.service;

import com.example.amattang.domain.answer.QuestionToAnswer;
import com.example.amattang.domain.answer.dto.*;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestion.MAIN_CATEGORY;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeA;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeB;
import com.example.amattang.domain.commonQuestion.CommonQuestionTypeD;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.amattang.exception.ExceptionMessage.NOT_ACCESS_USER;
import static com.example.amattang.exception.ExceptionMessage.NOT_EXIST_CHECK_LIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonQuestionService {

    private final CheckListRepository checkListRepository;
    private final EntityManager entityManager;
    private final ImageRepository imageRepository;

    public CommonCheckListDto getCheckListQuestionsWithAnswer(Long id, String main, String subCategory) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));

        List<ListToQuestion> listCommonQuestion = checkList.getListCommonQuestion();

        List<CommonQuestionDto> list1 = listCommonQuestion.stream()
                .filter(w -> !(main.equals(MAIN_CATEGORY.INSIDE.getMsg())) ?
                        w.getCommonQuestionId().getMainCategory().equals(main):
                                w.getCommonQuestionId().getMainCategory().equals(main) && w.getCommonQuestionId().getSubCategory().equals(subCategory)
                        )
                .map(x -> (!x.getCommonQuestionId().getAnsType().equals("C") && !x.getCommonQuestionId().getAnsType().equals("M") && x.getQuestionToAnswer() == null) ?
                        mapToDto(x.getCommonQuestionId(), x.isVisibility()) :
                        mapToDtoWithAnswer(x.getCommonQuestionId(), x.getQuestionToAnswer(), x.isVisibility(), checkList)
                )
                .collect(Collectors.toList());

        return CommonCheckListDto.create(checkList, list1);
    }

    public CommonQuestionDto mapToDtoWithAnswer(CommonQuestion question, QuestionToAnswer questionToAnswer, boolean visibility, CheckList checkList) {
        List answerDto = mapFromQuestionToTypeAnswer(question, questionToAnswer, checkList);
        return CommonQuestionDto.fromQuestion(question, answerDto, visibility);
    }

    public CommonQuestionDto mapToDto(CommonQuestion question, boolean visibility) {
        List answer = mapFromQuestionToTypeAnswer(question);
        return CommonQuestionDto.fromQuestion(question, answer, visibility);
    }

    public List<? extends Object> mapFromQuestionToTypeAnswer(CommonQuestion question) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromQuestion(entityManager.getReference(CommonQuestionTypeA.class, question.getId()));
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromQuestion(entityManager.getReference(CommonQuestionTypeB.class, question.getId()));
        } else if (question.getAnsType().equals("D")) {
            return AnswerDDto.fromQuestion(entityManager.getReference(CommonQuestionTypeD.class, question.getId()));
        } else {
            return null;
        }
    }
    public List<? extends Object> mapFromQuestionToTypeAnswer(CommonQuestion question, QuestionToAnswer questionToAnswer, CheckList checkList) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromAnswer(entityManager.getReference(CommonQuestionTypeA.class, question.getId()), questionToAnswer.getAnswerList(), questionToAnswer.getId());
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromAnswer(entityManager.getReference(CommonQuestionTypeB.class, question.getId()), questionToAnswer.getAnswerList(), questionToAnswer.getId());
        } else if (question.getAnsType().equals("C")) {
            List<Image> imageList = imageRepository.findAllByCheckListId_Id(checkList.getId());
            if (imageList.isEmpty()) return null;

            return IntStream.range(0, imageList.size())
                    .mapToObj(x -> new AnswerCDto(imageList.get(x).getId(), x, imageList.get(x).getUrl(), imageList.get(x).isMain()))
                    .collect(Collectors.toList());

        } else if (question.getAnsType().equals("D")) {
            return AnswerDDto.fromAnswer(entityManager.getReference(CommonQuestionTypeD.class, question.getId()), questionToAnswer.getAnswerList(), questionToAnswer.getId());
        } else {
            return AnswerMDto.fromEntity(checkList.getAddress(), checkList.getLatitude(), checkList.getLongitude());
        }
    }


    @Transactional
    public void updateCommonQuestionStatus(User user, Long id, CommonVisibilityRequestDto dto) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        List<ListToQuestion> listCommonQuestion = checkList.getListCommonQuestion();
        List<Question> question = dto.getQuestion();
        Collections.sort(question, (a,b) -> a.getQuestionId().compareTo(b.getQuestionId()));
        int i = 0;
        for (ListToQuestion q : listCommonQuestion) {
            if (i == question.size()) break;

            Question question1 = question.get(i);

            if (q.getCommonQuestionId().getId() == question1.getQuestionId()) {
                q.setVisibility(question1.getVisibility());
                if (!question1.getVisibility()) {
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
