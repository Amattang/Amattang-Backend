package com.example.amattang.domain.customCategory;

import com.example.amattang.domain.user.User;

import javax.persistence.*;

@Entity
public class CustomCategory {

    @Id
    @Column(name = "custom_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;
}
