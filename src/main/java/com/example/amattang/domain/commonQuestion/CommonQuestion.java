package com.example.amattang.domain.commonQuestion;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "question")
//    @Builder.Default
    private List<QuestionTemplate> templates = new ArrayList<>();

    @Getter
    public enum MAIN_CATEGORY {
        OUTSIDE("외부시설"),
        INSIDE("내부시설"),
        OPTIONS("옵션"),
        BASIC("기본정보");

        private String msg;

        MAIN_CATEGORY(String msg) {
            this.msg = msg;
        }
    }


}
