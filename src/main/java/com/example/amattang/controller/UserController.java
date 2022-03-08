package com.example.amattang.controller;

import com.example.amattang.payload.reponse.LoginResponseDto;
import com.example.amattang.payload.request.LoginRequestDto;
import com.example.amattang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인", response = LoginResponseDto.class)
    @PostMapping("/login")
    public LoginResponseDto test(@RequestBody LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = userService.loadUser(dto);
        return loginResponseDto;
    }

}
