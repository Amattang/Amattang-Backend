package com.example.amattang.domain.answer;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_b")
public class AnswerB extends Answer{

    @Id
    @Column(name = "ans_type_b_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    private String ans_type1;
    private String ans_type2;
    private String description_type1;
    private String description_type2;
}
