package com.example.amattang.domain.answer;

import javax.persistence.*;
@Entity
@Table(name = "answer_type_c")
public class AnswerC extends Answer{


    @Id
    @Column(name = "answer_type_c_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    private Boolean ansType1;
    private Boolean ansType2;
    private Boolean ansType3;

    private Boolean redType;
}
