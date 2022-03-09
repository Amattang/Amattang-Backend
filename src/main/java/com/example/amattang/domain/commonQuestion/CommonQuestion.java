package com.example.amattang.domain.commonQuestion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class CommonQuestion {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String mainCategory;
    private String subcategory;
    private String rule;
    private String description;
    private String emoji;

    @Enumerated(EnumType.STRING)
    private TYPE ansType;

    enum TYPE {
        A,B,C,D
    }
}
