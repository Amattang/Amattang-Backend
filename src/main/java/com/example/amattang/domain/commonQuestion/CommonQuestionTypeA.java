package com.example.amattang.domain.commonQuestion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@ToString
@NoArgsConstructor
@DiscriminatorValue("A")
public class CommonQuestionTypeA extends CommonQuestion{


    private Boolean redType;

}
