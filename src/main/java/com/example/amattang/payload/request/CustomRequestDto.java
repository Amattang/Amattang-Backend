package com.example.amattang.payload.request;

import com.example.amattang.controller.ValidationGroup.generalGroup;
import com.example.amattang.controller.ValidationGroup.saveGroup;
import com.example.amattang.controller.ValidationGroup.updateGroup;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.customCategory.CustomCategory;
import com.example.amattang.domain.customQuestion.CustomQuestion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CustomRequestDto {

    @NotNull(groups = updateGroup.class, message = "카테고리 아이디가 누락되었습니다.")
    @Null(groups = saveGroup.class, message = "카테고리 생성 시에는 카테고리 아이디를 입력할 수 없습니다.")
    @ApiModelProperty(name = "카테고리 아이디", example = "1", notes = "put 요청시에만 포함")
    private Long categoryId;

    @NotNull(groups = generalGroup.class, message = "카테고리 이름이 누락되었습니다.")
    @ApiModelProperty(name = "카테고리 이름", example = "집주인한테 물어볼 것들")
    private String categoryName;

    @Valid
    List<QuestionDto> questions;

    @Data
    @ApiModel(description = "custom 카테고리의 질문")
    static class QuestionDto {

        @Null(groups = saveGroup.class, message = "질문 생성 시에는 질문 아이디를 입력할 수 없습니다.")
        @ApiModelProperty(name = "카테고리 질문 아이디", example = "1 (post 요청 시에는 입력하지 않음)")

        private Long questionId;

        @NotNull(groups = generalGroup.class, message = "질문 내용이 누락되었습니다.")
        @ApiModelProperty(name = "카테고리 질문", example = "원래 날짜보다 앞당겨서 입주가능한가요?")
        private String content;

        @NotNull(groups = generalGroup.class, message = "질문 체크 여부가 누락되었습니다/")
        @ApiModelProperty(name = "카테고리 질문 체크 여부", example = "false")
        private Boolean checked;

        public CustomQuestion toEntity() {
            return CustomQuestion.builder()
                    .id(this.questionId)
                    .question(this.content)
                    .checked(this.checked)
                    .build();
        }

        public CustomQuestion toEntity(CustomCategory customCategory) {
            return CustomQuestion.builder()
                    .id(this.questionId)
                    .question(this.content)
                    .checked(this.checked)
                    .customCategoryId(customCategory)
                    .build();
        }

    }

    //아래 항목이 없을 때를 확인하기
    public CustomCategory toEntity(CheckList checkList) {
        CustomCategory category = CustomCategory.builder()
                .id(this.categoryId)
                .name(this.categoryName)
                .checkListId(checkList)
                .customQuestions(
                        this.questions.stream()
                                .map(x -> x.toEntity())
                                .collect(Collectors.toSet())
                )
                .build();
        category.getCustomQuestions().stream()
                .forEach(x -> x.setCustomCategoryId(category));
        return category;
    }

    public void updateCategory(CustomCategory category) {
        category.setName(this.categoryName);
        category.setCustomQuestions(
                this.questions.stream()
                        .map(x -> x.toEntity(category))
                        .collect(Collectors.toSet())
        );
    }


}
