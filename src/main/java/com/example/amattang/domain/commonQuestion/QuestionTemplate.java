package com.example.amattang.domain.commonQuestion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "common_question_type_d_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionTemplate {

    @Id
    @Column(name = "common_question_type_d_template_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "common_question_question_id")
    private CommonQuestionTypeD question;

    private String template;
}
