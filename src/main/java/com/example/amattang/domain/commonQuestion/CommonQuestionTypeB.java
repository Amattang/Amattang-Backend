package com.example.amattang.domain.commonQuestion;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("B")
public class CommonQuestionTypeB extends CommonQuestion{


    private String direction1;
    private String direction2;

}
