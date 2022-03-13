package com.example.amattang.domain.listToQuestion;

import com.example.amattang.domain.answer.Answer;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.commonQuestion.CommonQuestion;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
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

    private Boolean visibility;

    @OneToOne(mappedBy = "listToQuestionId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Answer answer;

}
