package com.example.amattang.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CommonVisibilityRequestDto {

    @Size(min = 1, message = "question을 입력해주세요")
    List<Question> question;

    @Data
    public static class Question {
        @NotNull(message = "삭제(추가)할 질문 아이디가 누락되었습니다.")
        @ApiModelProperty(name = "삭제(추가)할 질문 아이디", example = "1")
        private Long questionId;

        @NotNull(message = "질문 삭제 여부가 누락되었습니다.")
        @ApiModelProperty(name = "질문 삭제 여부", example = "true")
        private Boolean visibility;
    }

}
