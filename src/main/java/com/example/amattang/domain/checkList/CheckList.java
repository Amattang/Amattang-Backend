package com.example.amattang.domain.checkList;

import com.example.amattang.domain.customCategory.CustomCategory;
import com.example.amattang.domain.image.Image;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import com.example.amattang.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckList {

    @Id
    @Column(name = "check_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id")
    private User user;

    private Boolean pinned;

    @Setter
    @OneToMany(mappedBy = "checkListId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    List<ListToQuestion> listCommonQuestion = new ArrayList<>();

    @OneToMany(mappedBy = "checkListId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    Set<CustomCategory> customCategories = new HashSet<>();

    @OneToMany(mappedBy = "checkListId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    Set<Image> imageList = new HashSet<>();

    public CheckList(User user, Boolean pinned) {
        this.user = user;
        this.pinned = pinned;
    }
}
