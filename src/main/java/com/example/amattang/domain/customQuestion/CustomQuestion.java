package com.example.amattang.domain.customQuestion;

import com.example.amattang.domain.checkList.CheckList;

import javax.persistence.*;

@Entity
public class CustomQuestion {

    @Id
    @Column(name = "custom_question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "check_list_check_list_id")
    private CheckList checkListId;

    private String question;
    private Boolean checked;
}
