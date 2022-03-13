package com.example.amattang.domain.answer;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_list_common_question_check_list_common_question_id")
    private ListToQuestion listToQuestionId;

}
