package com.example.amattang.domain.checkList;

import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CheckListRepositoryTest {


    @Autowired
    private CommonQuestionRepository questionRepository;

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private UserRepository userRepository;

    User init() {
        User user = User.builder()
                .name("테스트")
                .provider(User.PROVIDER.KAKAO)
                .id("KAKAO_1234")
                .build();
        return userRepository.save(user);
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("체크리스트 생성 시, 모든 question 관계 생성 테스트")
    public void test() {
        //given
        CheckList checkList = new CheckList(init(), false);
        List<CommonQuestion> question = questionRepository.findAll();

        List<ListToQuestion> relationSet = question.stream()
                .map(x -> ListToQuestion.builder()
                        .commonQuestionId(x)
                        .checkListId(checkList)
                        .visibility(true)
                        .build()
                )
                .collect(Collectors.toList());

        checkList.setListCommonQuestion(relationSet);
        checkListRepository.save(checkList);

        //when
        CheckList byId = checkListRepository.getById(checkList.getId());

        //then
        assertThat(byId.getListCommonQuestion().size()).isEqualTo(questionRepository.findAll().size());
    }



}