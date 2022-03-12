package com.example.amattang.domain.commonQuestion;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Table(name = "common_question_type_d_template")
@AllArgsConstructor
public class QuestionTemplate {

    @Column(name = "common_question_type_d_template_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "common_question_question_id")
    private CommonQuestionTypeD question;

    private String template;
}
