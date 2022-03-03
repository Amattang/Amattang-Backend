package com.example.amattang.domain.answer;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_d")
public class AnswerD extends Answer{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_answer_id")
    private Answer answerId;

    @Lob
    private byte[] ans;
}
