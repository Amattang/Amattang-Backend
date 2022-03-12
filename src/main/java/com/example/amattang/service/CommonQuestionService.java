package com.example.amattang.service;

import com.example.amattang.domain.answer.dto.AnswerADto;
import com.example.amattang.domain.answer.dto.AnswerBDto;
import com.example.amattang.domain.answer.dto.AnswerCDto;
import com.example.amattang.domain.answer.dto.AnswerDDto;
import com.example.amattang.domain.commonQuestion.*;
import com.example.amattang.payload.reponse.CommonQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonQuestionService {

    private final CommonQuestionRepository questionRepository;

    public List<CommonQuestionDto> getQuestionByCategory(String mainCategory) {
        List<CommonQuestion> questionList = questionRepository.findByMainCategory(mainCategory, Sort.by(Sort.Direction.ASC, "id"));
        List<CommonQuestionDto> checkListDtoList = questionList.stream()
                .filter(x -> x.getMainCategory().equals(mainCategory))
                .map(x -> mapToDto(x))
                .collect(Collectors.toList());
        return checkListDtoList;
    }

    public List<CommonQuestionDto> getQuestionByCategory(String mainCategory, String subcateogry) {
        List<CommonQuestion> questionList = questionRepository.findByMainCategory(mainCategory, Sort.by(Sort.Direction.ASC, "id"));
        List<CommonQuestionDto> checkListDtoList = questionList.stream()
                .filter(x -> x.getMainCategory().equals(mainCategory))
                .map(x -> mapToDto(x))
                .collect(Collectors.toList());
        return checkListDtoList;
    }



    public CommonQuestionDto mapToDto(CommonQuestion question) {
        Object answer = mapFromQuestionToTypeAnswer(question);
        return CommonQuestionDto.fromQuestion(question, answer);
    }

    public Object mapFromQuestionToTypeAnswer(CommonQuestion question) {
        if (question.getAnsType().equals("A")) {
            return AnswerADto.fromQuestion((CommonQuestionTypeA) question);
        } else if (question.getAnsType().equals("B")) {
            return AnswerBDto.fromQuestion((CommonQuestionTypeB) question);
        } else if (question.getAnsType().equals("C")) {
            return AnswerCDto.fromQuestion((CommonQuestionTypeC) question);
        } else {
            return AnswerDDto.fromQuestion((CommonQuestionTypeD) question);
        }
    }
}
