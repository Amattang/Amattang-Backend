package com.example.amattang.domain.user;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @Enumerated(EnumType.STRING)
    private PROVIDER provider;

    private String name;

    public enum PROVIDER {
        KAKAO, APPLE
    }

}
