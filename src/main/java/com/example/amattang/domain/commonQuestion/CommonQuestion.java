package com.example.amattang.domain.commonQuestion;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "d_type")
public abstract class CommonQuestion {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String mainCategory;
    private String subCategory;
    private String rule;
    private String description;
    private String emoji;
    private String basicType;

    @Column(name = "ans_type")
    private String ansType;

}
