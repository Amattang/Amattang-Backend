package com.example.amattang.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AppleLoginRequestDto {

    @NotBlank(message = "user는 빈 값이 허용되지 않습니다.")
    @ApiModelProperty(name = "애플에서 제공하는 사용자 아이디", example = "1234.sijdc9i03e.1234")
    private String user;

    @NotBlank(message = "email은 빈 값이 허용되지 않습니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    @ApiModelProperty(name = "사용자 이메일", example = "jaery@gmail.com")
    private String email;

}
