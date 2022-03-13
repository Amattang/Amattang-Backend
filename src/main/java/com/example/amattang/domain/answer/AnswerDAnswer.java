package com.example.amattang.domain.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer_type_d_answer")
public class AnswerDAnswer {


    @Id
    @Column(name = "answer_type_d_answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_type_d_answer_type_d_id")
    private AnswerD answerId;

    private String type;

    private Boolean ans;


}
