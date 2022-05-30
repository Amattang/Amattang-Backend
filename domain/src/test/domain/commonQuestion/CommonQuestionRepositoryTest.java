package com.example.amattang.domain.commonQuestion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommonQuestionRepositoryTest {

    @Autowired
    CommonQuestionRepository questionRepository;

    @Test
    @DisplayName("카테고리 별, 아이디 오름차순 조회")
    public void test() {

        List<CommonQuestion> byMainCategory = questionRepository.findByMainCategory("외부시설", Sort.by(Sort.Direction.ASC, "id"));
        for (CommonQuestion q : byMainCategory) {
            System.out.println(q.getId());
        }
    }

}