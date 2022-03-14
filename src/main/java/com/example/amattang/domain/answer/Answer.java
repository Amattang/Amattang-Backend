package com.example.amattang.domain.answer;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Answer {


    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private boolean redType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_to_answer_question_to_answer_id")
    private QuestionToAnswer questionToAnswer;

    public Answer(String type, boolean redType, QuestionToAnswer questionToAnswer) {
        this.type = type;
        this.redType = redType;
        this.questionToAnswer = questionToAnswer;
    }
}
