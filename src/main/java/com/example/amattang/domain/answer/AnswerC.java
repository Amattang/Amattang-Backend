package com.example.amattang.domain.answer;

import javax.persistence.*;
@Entity
@Table(name = "answer_type_c")
public class AnswerC extends Answer{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    private Boolean type1;
    private Boolean type2;
    private Boolean type3;

    private Boolean redType;
}
