package com.example.amattang.domain.answer;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_b")
public class AnswerB extends Answer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    private String ans1;
    private String ans2;
    private String direct1;
    private String direct2;
}
