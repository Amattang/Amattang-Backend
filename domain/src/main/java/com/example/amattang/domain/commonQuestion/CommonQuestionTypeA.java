package com.example.amattang.domain.commonQuestion;

import com.example.amattang.domain.answer.dto.REDTYPE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Entity
@ToString
@NoArgsConstructor
@DiscriminatorValue("A")
public class CommonQuestionTypeA extends CommonQuestion{

    @Enumerated(EnumType.STRING)
    private REDTYPE redType;


}
