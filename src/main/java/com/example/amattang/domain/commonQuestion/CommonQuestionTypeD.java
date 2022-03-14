package com.example.amattang.domain.commonQuestion;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("D")
public class CommonQuestionTypeD extends CommonQuestion{

    private Boolean duplication;


}
