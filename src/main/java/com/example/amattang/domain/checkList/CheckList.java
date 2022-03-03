package com.example.amattang.domain.checkList;

import com.example.amattang.domain.user.User;

import javax.persistence.*;

@Entity
public class CheckList {

    @Id
    @Column(name = "check_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    private Boolean pinned;
}
