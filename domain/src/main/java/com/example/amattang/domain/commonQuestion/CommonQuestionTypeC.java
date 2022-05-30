package com.example.amattang.domain.commonQuestion;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("C")
public class CommonQuestionTypeC extends CommonQuestion{


}
