package com.example.amattang.domain.commonQuestion;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        public static Optional<MAIN_CATEGORY> fromMsg(String msg) {
            switch (msg) {
                case "외부시설":
                    return Optional.of(OUTSIDE);
                case "내부시설":
                    return Optional.of(INSIDE);
                case "옵션":
                    return Optional.of(OPTIONS);
                case "기본정보":
                    return Optional.of(BASIC);
                default:
                    return Optional.ofNullable(null);
            }
        }
    }


    @Getter
    public enum SUB_CATEGORY {
        WALL("벽"),
        KITCHEN("부엌"),
        WINDOW("창문"),
        ENTRANCE("현관"),
        RESTROOM("화장실"),
        NONE("none");

        private String msg;

        SUB_CATEGORY(String msg) {
            this.msg = msg;
        }

        public static Optional<SUB_CATEGORY> fromMsg(String msg) {
            switch (msg) {
                case "벽":
                    return Optional.of(WALL);
                case "부엌":
                    return Optional.of(KITCHEN);
                case "창문":
                    return Optional.of(WINDOW);
                case "현관":
                    return Optional.of(ENTRANCE);
                case "화장실":
                    return Optional.of(RESTROOM);
                default:
                    return Optional.ofNullable(null);
            }
        }
    }


}
