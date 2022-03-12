package com.example.amattang.domain.checkList;

import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckList {

    @Id
    @Column(name = "check_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    private Boolean pinned;

    @Setter
    @OneToMany(mappedBy = "checkListId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    Set<ListToQuestion> listCommonQuestion = new HashSet<>();

    public CheckList(User user, Boolean pinned) {
        this.user = user;
        this.pinned = pinned;
    }
}
