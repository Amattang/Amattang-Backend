package com.example.amattang.domain.customQuestion;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.customCategory.CustomCategory;
import com.example.amattang.domain.customCategory.CustomCategoryRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.payload.request.CustomRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Set;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomQuestionRepositoryTest {

    @Autowired
    private CustomQuestionRepository questionRepository;

    @Autowired
    private CustomCategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;


    public Long init() {
        User build = User.builder()
                .provider(User.PROVIDER.KAKAO)
                .id("kakao_dd")
                .name("kakao")
                .build();

        entityManager.persist(build);

        CheckList build1 = CheckList.builder()
                .user(build)
                .pinned(false)
                .build();
        entityManager.persist(build1);
        build.getCheckLists().add(build1);

        CustomCategory category = CustomCategory.builder()
                .name("할 일 목록")
                .checkListId(build1)
                .build();
        entityManager.persist(category);
        Set<CustomCategory> customCategories = build1.getCustomCategories();
        System.out.println(customCategories + "   ");
        build1.getCustomCategories().add(category);

        CustomQuestion question = CustomQuestion.builder()
                .question("밥 먹기")
                .checked(true)
                .customCategoryId(category)
                .build();

        entityManager.persist(question);
        category.getCustomQuestions().add(question);

        return category.getId();
    }

    @Test
    @Rollback(false)
    @DisplayName("커스텀 카테고리 수정 테스트")
    public void test() {

        Long aLong = init();
        CustomCategory category = categoryRepository.getById(aLong);

        CustomRequestDto.QuestionDto build2 = CustomRequestDto.QuestionDto.builder()
                .content("할일 하자!!")
                .checked(false)
                .build();

        CustomRequestDto request = CustomRequestDto.builder()
                .categoryId(category.getId())
                .categoryName("할 일 목록")
                .questions(Arrays.asList(build2))
                .build();

        categoryRepository.save(category);

    }
}