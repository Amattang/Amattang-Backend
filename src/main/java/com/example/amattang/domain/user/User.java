package com.example.amattang.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private PROVIDER provider;

    private String name;

    private String email;

    enum PROVIDER {
        KAKAO, APPLE
    }

}
