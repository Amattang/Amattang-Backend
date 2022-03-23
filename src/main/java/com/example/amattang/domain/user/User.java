package com.example.amattang.domain.user;

import com.example.amattang.domain.checkList.CheckList;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public enum ROLE {
        USER
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<CheckList> checkLists = new ArrayList<>();


}
