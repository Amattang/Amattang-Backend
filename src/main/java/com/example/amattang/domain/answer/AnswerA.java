package com.example.amattang.domain.answer;

import com.example.amattang.domain.commonQuestion.CommonQuestion;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_a")
public class AnswerA extends Answer{

    @Id
    @Column(name = "answer_type_a_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    private Boolean ansTrue;

    private Boolean ansFalse;
    private String redType;


}
