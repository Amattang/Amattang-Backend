package com.example.amattang.domain.answer;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import com.example.amattang.domain.commonQuestion.CommonQuestionRepository;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.listToQuestion.ListToQuestionRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import com.example.amattang.service.CommonQuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.NOT_EXIST_CHECK_LIST;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private CommonQuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonQuestionRepository questionRepository;

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private QuestionToAnswerRepository questionToAnswerRepository;

    @Autowired
    private ListToQuestionRepository listToQuestionRepository;


    User init() {
        User user = User.builder()
                .name("테스트")
                .provider(User.PROVIDER.KAKAO)
                .id("KAKAO_1234")
                .build();
        return userRepository.save(user);
    }

    CheckList getCheckList() {
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
        return checkListRepository.save(checkList);
    }

    @Test
//    @Transactional
    public void test() {
//        List<CommonQuestionDto> list = new ArrayList<>();
//
//        CheckList checkList = getCheckList();
//        List<ListToQuestion> questionList = checkList.getListCommonQuestion();
//        for (ListToQuestion q : questionList) {
//            list.add((q.getQuestionToAnswer().getAnswerList() == null) ?
//                    questionService.mapToDto(q.getCommonQuestionId()) :
//                    questionService.mapToDtoWithAnswer(q.getCommonQuestionId(), q.getQuestionToAnswer()
//                    ));
//        }
    }

    @Test
//    @Transactional
    public void test1() {

//        CheckList checkList = getCheckList();
//        CheckList checkList1 = checkListRepository.findById(checkList.getId()).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
//
//        List<ListToQuestion> questionList = checkList1.getListCommonQuestion();
//
//        List<CommonQuestionDto> list1 = questionList.stream()
//                .filter(w -> w.getCommonQuestionId().getMainCategory().equals("외부시설"))
//                .map(x -> x.getQuestionToAnswer().getAnswerList() == null ?
//                        questionService.mapToDto(x.getCommonQuestionId()) :
//                        questionService.mapToDtoWithAnswer(x.getCommonQuestionId(), x.getQuestionToAnswer())
//                )
//                .collect(Collectors.toList());
//        System.out.println(list1);

    }

    @Test
    @Transactional //rollback
    @DisplayName("answer 하위 타입 저장")
    public void saveBoolType() {
        User user = userRepository.findAll().get(0);
        CheckList checklist = CheckList.builder()
                .user(user)
                .pinned(false)
                .build();
        CommonQuestion boolQuestion = questionRepository.getById(26L);
        ListToQuestion listToQuestion = ListToQuestion.builder()
                .checkListId(checklist)
                .commonQuestionId(boolQuestion)
                .visibility(true)
                .build();

        checklist.setListCommonQuestion(Arrays.asList(listToQuestion));

        CheckList save = checkListRepository.save(checklist);

        QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);
        AnswerBool bool = new AnswerBool("예", true, questionToAnswer, true);
        AnswerBool bool2 = new AnswerBool("아니오", false, questionToAnswer, false);
        List<AnswerBool> list = new ArrayList<>(Arrays.asList(bool, bool2));
        questionToAnswer.setAnswerBoolList(list);
        questionToAnswerRepository.save(questionToAnswer);

        assertThat(answerRepository.findAll().size()).isEqualTo(2);
    }


    @Test
    @Transactional
    @DisplayName("answer 삭제 시, 하위 타입도 같이 삭제되는지 테스트")
    public void answerInheritanceTest() {
        User user = userRepository.findAll().get(0);
        CheckList checklist = CheckList.builder()
                .user(user)
                .pinned(false)
                .build();
        CommonQuestion boolQuestion = questionRepository.getById(26L);
        ListToQuestion listToQuestion = ListToQuestion.builder()
                .checkListId(checklist)
                .commonQuestionId(boolQuestion)
                .visibility(true)
                .build();

        checklist.setListCommonQuestion(Arrays.asList(listToQuestion));

        CheckList save = checkListRepository.save(checklist);

        QuestionToAnswer questionToAnswer = new QuestionToAnswer(listToQuestion);
        AnswerBool bool = new AnswerBool("예", true, questionToAnswer, true);
        AnswerBool bool2 = new AnswerBool("아니오", false, questionToAnswer, false);
        List<AnswerBool> list = new ArrayList<>(Arrays.asList(bool, bool2));
        questionToAnswer.setAnswerBoolList(list);
        QuestionToAnswer save1 = questionToAnswerRepository.save(questionToAnswer);

        questionToAnswerRepository.deleteById(save1.getId());

        assertThat(answerRepository.findAll().size()).isEqualTo(0);
        List<AnswerBool> answerList = entityManager.createQuery("select m from AnswerBool m", AnswerBool.class).getResultList();
        assertThat(answerList.size()).isEqualTo(0);
    }
}