package com.example.amattang.domain.commonQuestion;


import com.example.amattang.domain.answer.dto.REDTYPE;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("C")
public class CommonQuestionTypeC extends CommonQuestion{

    @Enumerated(EnumType.STRING)
    private REDTYPE redType;
    @Column(name = "type2_exists")
    private Boolean type2Exists;
//    private Boolean duplication;


}
