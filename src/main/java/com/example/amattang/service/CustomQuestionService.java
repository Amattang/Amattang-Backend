package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.customCategory.CustomCategory;
import com.example.amattang.domain.customCategory.CustomCategoryRepository;
import com.example.amattang.domain.customQuestion.CustomQuestion;
import com.example.amattang.domain.customQuestion.CustomQuestionRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.payload.reponse.CustomCheckListResponseDto;
import com.example.amattang.payload.request.CustomRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomQuestionService {

    private final CustomCategoryRepository categoryRepository;
    private final CheckListRepository checkListRepository;
    private final CustomQuestionRepository questionRepository;

    public List<CustomCheckListResponseDto> getAllCategoryAndAnswer(User user, Long checkListId) {
        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        return checkList.getCustomCategories().stream()
                .map(x -> CustomCheckListResponseDto.fromEntity(x))
                .collect(Collectors.toList());
    }

    public void saveCategoryAndAnswer(User user, Long checkListId, CustomRequestDto dto) {
        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        CustomCategory customCategory = dto.toEntity(checkList);
        categoryRepository.save(customCategory);
    }


    @Transactional
    public void updateCategoryAndAnswer(User user, Long checkListId, CustomRequestDto dto) {
        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());

        CustomCategory customCategory = checkList.getCustomCategories().stream()
                .filter(x -> x.getId().equals(dto.getCategoryId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 사용자에 존재하지 않는 커스텀 카테고리 아이디입니다. id => " + dto.getCategoryId()));

        //여기 cascade가 안돼서 따로 저장
        customCategory.setName(dto.getCategoryName());
        customCategory.getCustomQuestions().clear();
        categoryRepository.save(customCategory);

        try {
            Set<CustomQuestion> questionSet = dto.getQuestions().stream()
                    .map(x -> x.toEntity(customCategory))
                    .collect(Collectors.toSet());
            customCategory.getCustomQuestions().addAll(questionSet);
        } catch (NullPointerException e) {
            log.debug(e.getMessage());
        }

    }


    public void deleteCategories(User user, Long checkListId, List<Long> categoryIds) {
        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        categoryIds.stream()
                .forEach(x -> categoryRepository.findByIdAndCheckListId_Id(x, checkListId)
                        .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CUSTOM_CATEGORY_WITH_USER + x))
                );

        categoryRepository.deleteAllById(categoryIds);
    }


    public void isCorrectAuthor(String userId, String authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAccessUserException(NOT_ACCESS_USER);
        }
    }


}
