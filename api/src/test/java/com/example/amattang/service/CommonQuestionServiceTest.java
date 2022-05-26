package com.example.amattang.service;

import com.example.amattang.domain.answer.dto.AnswerADto;
import com.example.amattang.domain.answer.dto.AnswerBDto;
import com.example.amattang.domain.answer.dto.AnswerCDto;
import com.example.amattang.domain.answer.dto.AnswerDDto;
import com.example.amattang.domain.commonQuestion.*;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommonQuestionServiceTest {


    @Autowired
    private CommonQuestionRepository questionRepository;

    @Nested
    @DisplayName("답변이 없는 질문 매핑 테스트")
    class QuestionMappingTestNoAnswer {

        @Test
        @DisplayName("A 타입")
        void typeATest(){
            //given
//            List<CommonQuestion> question = questionRepository.findAll();
//
//            //when
//            for (CommonQuestion q : question) {
//                if (q.getAnsType().equals("A")) {
//                    AnswerADto answerADto = AnswerADto.fromQuestion((CommonQuestionTypeA) q);
//                    System.out.println(answerADto.toString());
//                    CommonQuestionDto commonQuestionDto = CommonQuestionDto.fromQuestion(q, answerADto);
//                    System.out.println(commonQuestionDto.toString());
//                }
//            }
        }

        @Test
        @DisplayName("B 타입")
        void typeBTest(){
            //given
            List<CommonQuestion> question = questionRepository.findAll();

            //when
            for (CommonQuestion q : question) {
                if (q.getAnsType().equals("B")) {
//                    AnswerBDto answerBDto = AnswerBDto.fromQuestion((CommonQuestionTypeB) q);
////                    System.out.println(answerBDto.toString());
//                    CommonQuestionDto commonQuestionDto = CommonQuestionDto.fromQuestion(q, answerBDto);
////                    System.out.println(commonCheckListDto.toString());
//                    break;
                }
            }
        }


        @Test
        @DisplayName("D 타입")
        void typeDTest(){
            //given
            List<CommonQuestion> question = questionRepository.findAll();

            //when
            for (CommonQuestion q : question) {
                if (q.getAnsType().equals("D")) {
//                    AnswerDDto answerDDto = AnswerDDto.fromQuestion((CommonQuestionTypeD) q);
////                    System.out.println(answerDDto.toString());
//                    CommonQuestionDto commonQuestionDto = CommonQuestionDto.fromQuestion(q, answerDDto);
////                    System.out.println(commonCheckListDto.toString());
//                    break;
                }
            }
        }
    }

}