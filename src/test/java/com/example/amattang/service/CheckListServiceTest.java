package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.NOT_EXIST_CHECK_LIST;

@ActiveProfiles("local")
//@ExtendWith(MockitoExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class CheckListServiceTest {

    @Autowired
    private CommonQuestionService questionService;

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonQuestionRepository questionRepository;


    User user_init() {
        User user = User.builder()
                .name("테스트")
                .provider(User.PROVIDER.KAKAO)
                .id("KAKAO_1234")
                .build();
        return userRepository.save(user);
    }

    @BeforeEach
    void init() {
        CheckList checkList = new CheckList(user_init(), false);
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
    }


}