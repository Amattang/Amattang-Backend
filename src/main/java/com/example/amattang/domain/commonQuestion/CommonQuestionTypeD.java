package com.example.amattang.domain.commonQuestion;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("D")
public class CommonQuestionTypeD extends CommonQuestion{

    private boolean duplication;

    @OneToMany(mappedBy = "question")
//    @Builder.Default
    private Set<QuestionTemplate> templates = new HashSet<>();

}
