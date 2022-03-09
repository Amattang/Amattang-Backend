package com.example.amattang.controller;

import com.example.amattang.payload.reponse.LoginResponseDto;
import com.example.amattang.payload.reponse.ReIssueTokenResponseDto;
import com.example.amattang.payload.request.LoginRequestDto;
import com.example.amattang.payload.request.ReIssueTokenRequestDto;
import com.example.amattang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.amattang.payload.reponse.ResponseUtil.succes;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인", response = LoginResponseDto.class)
    @PostMapping("/login")
    public ResponseEntity<?> loginByAccessToken(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = userService.loadUser(dto);
        return succes(loginResponseDto, "로그인 성공");
    }

    @ApiOperation(value = "토큰 재발급", response = ReIssueTokenResponseDto.class)
    @PostMapping("/issue/re")
    public ResponseEntity<?> reIssueToken(@RequestBody @Valid ReIssueTokenRequestDto dto) {
        ReIssueTokenResponseDto response = userService.reIssueToken(dto);
        return succes(response, dto.getRequest() + " 토큰 재발급 성공");
    }


}
