package com.example.amattang.domain.basicQuestion;

import com.example.amattang.domain.commonQuestion.CommonQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BasicQuestion extends CommonQuestion {

    @Id
    @Column(name = "basic_question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "common_question_question_id")
    private CommonQuestion commonQuestionId;

    private String basicType;
}
