package com.example.amattang.domain.listToQuestion;

import com.example.amattang.domain.answer.QuestionToAnswer;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_list_common_question")
public class ListToQuestion {

    @Id
    @Column(name = "check_list_common_question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_list_check_list_id")
    private CheckList checkListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "common_question_question_id")
    private CommonQuestion commonQuestionId;

    @Setter
    private boolean visibility;

    @Setter
    @OneToOne(mappedBy = "listToQuestion", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private QuestionToAnswer questionToAnswer;

    public void deleteAnswer() {
        this.questionToAnswer = null;
    }

}
