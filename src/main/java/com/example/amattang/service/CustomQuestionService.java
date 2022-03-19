package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.customCategory.CustomCategory;
import com.example.amattang.domain.customCategory.CustomCategoryRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.payload.reponse.CustomCheckListResponseDto;
import com.example.amattang.payload.request.CustomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class CustomQuestionService {

    private final CustomCategoryRepository categoryRepository;
    private final CheckListRepository checkListRepository;

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

    public void updateCategoryAndAnswer(User user, Long checkListId, CustomRequestDto dto) {
        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());

        CustomCategory customCategory = checkList.getCustomCategories().stream()
                .filter(x -> x.getId() == dto.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 커스텀 카테고리 아이디입니다."));
        dto.updateCategory(customCategory);
        categoryRepository.save(customCategory);
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
