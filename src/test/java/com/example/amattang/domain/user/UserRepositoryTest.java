package com.example.amattang.domain.user;

import com.example.amattang.domain.answer.AnswerBool;
import com.example.amattang.domain.answer.AnswerString;
import com.example.amattang.domain.answer.QuestionToAnswer;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionRepository;
import com.example.amattang.domain.customCategory.CustomCategory;
import com.example.amattang.domain.customQuestion.CustomQuestion;
import com.example.amattang.domain.image.Image;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonQuestionRepository questionRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("회원 탈퇴 테스트")
    public void deleteUser() {
        User user = User.builder()
                .id("KAKAO_12345678")
                .name("kakao")
                .provider(User.PROVIDER.KAKAO)
                .build();

        userRepository.save(user);
        CheckList checkList = CheckList.builder()
                .user(user)
                .pinned(true)
                .build();
        entityManager.persist(checkList);
        user.getCheckLists().add(checkList);

        CustomCategory category = CustomCategory.builder()
                .checkListId(checkList)
                .name("테스트 카테고리")
                .build();
        checkList.getCustomCategories().add(category);

        CustomQuestion question1 = CustomQuestion.builder()
                .customCategoryId(category)
                .checked(false)
                .question("테스트1")
                .build();
        CustomQuestion question2 = CustomQuestion.builder()
                .customCategoryId(category)
                .checked(false)
                .question("테스트2")
                .build();

        HashSet<CustomQuestion> questions = new HashSet<>() {{
            add(question1);
            add(question2);
        }};

        category.setCustomQuestions(questions);

        Image image = new Image(checkList, "http://url_test");
        checkList.getImageList().add(image);

        CommonQuestion common = questionRepository.getById(1L);
        CommonQuestion common2 = questionRepository.getById(2L);

        ListToQuestion relation = ListToQuestion.builder()
                .commonQuestionId(common)
                .checkListId(checkList)
                .visibility(true)
                .build();
        ListToQuestion relation2 = ListToQuestion.builder()
                .commonQuestionId(common2)
                .checkListId(checkList)
                .visibility(true)
                .build();

        checkList.getListCommonQuestion().add(relation);
        checkList.getListCommonQuestion().add(relation2);

        QuestionToAnswer questionToAnswer = QuestionToAnswer.builder().listToQuestion(relation).build();
        QuestionToAnswer questionToAnswer2 = QuestionToAnswer.builder().listToQuestion(relation2).build();

        AnswerBool a = new AnswerBool("A", true, questionToAnswer, true);
        questionToAnswer.getAnswerList().add(a);

        AnswerString answerString = new AnswerString("B", questionToAnswer2, "str");
        questionToAnswer2.getAnswerList().add(answerString);

        relation.setQuestionToAnswer(questionToAnswer);
        relation2.setQuestionToAnswer(questionToAnswer2);

        entityManager.flush();
        entityManager.clear();

        CheckList checkList1 = entityManager.find(CheckList.class, checkList.getId());
        System.out.println(checkList1.getId());
        userRepository.deleteById(user.getId());
    }
}