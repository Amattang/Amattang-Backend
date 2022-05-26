package com.example.amattang.domain.customCategory;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.customQuestion.CustomQuestion;
import com.example.amattang.domain.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomCategory {

    @Id
    @Column(name = "custom_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "check_list_check_list_id")
    private CheckList checkListId;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "customCategoryId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    Set<CustomQuestion> customQuestions = new HashSet<>();

}
