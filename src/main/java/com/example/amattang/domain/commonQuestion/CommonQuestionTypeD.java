package com.example.amattang.domain.commonQuestion;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("D")
public class CommonQuestionTypeD extends CommonQuestion{

    private Boolean duplication;


    @OneToMany(mappedBy = "question")
//    @Builder.Default
    private List<QuestionTemplate> templates = new ArrayList<>();

}
