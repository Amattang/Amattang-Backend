package com.example.amattang.domain.answer;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_a")
public class AnswerA extends Answer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    private Boolean ans;
    private String redType;

}
