package com.example.amattang.domain.customQuestion;

import com.example.amattang.domain.customCategory.CustomCategory;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomQuestion {

    @Id
    @Column(name = "custom_question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_category_custom_category_id")
    private CustomCategory customCategoryId;

    private String question;
    private Boolean checked;
}
