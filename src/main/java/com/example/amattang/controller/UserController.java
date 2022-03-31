package com.example.amattang.controller;

import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.LoginResponseDto;
import com.example.amattang.payload.reponse.ReIssueTokenResponseDto;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.payload.request.AppleLoginRequestDto;
import com.example.amattang.payload.request.LoginRequestDto;
import com.example.amattang.payload.request.ReIssueTokenRequestDto;
import com.example.amattang.security.CurrentUser;
import com.example.amattang.security.UserPrincipal;
import com.example.amattang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @ApiOperation(value = "로그인", response = LoginResponseDto.class)
    @PostMapping("/login")
    public ResponseEntity<?> loginByAccessToken(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = userService.loadUser(dto);
        return ResponseUtil.success(loginResponseDto, "로그인 성공");
    }

    @ApiOperation(value = "회원 탈퇴", response = LoginResponseDto.class)
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        userRepository.deleteById(user.getId());
        return ResponseUtil.success("회원 탈퇴 성공", HttpStatus.OK);
    }

    @ApiOperation(value = "애플 로그인", response = LoginResponseDto.class)
    @PostMapping("/login/apple")
    public ResponseEntity<?> loginAppleUser(@RequestBody @Valid AppleLoginRequestDto dto) {
        LoginResponseDto loginResponseDto = userService.loadUser(dto);
        return ResponseUtil.success(loginResponseDto, "로그인 성공");
    }

    @ApiOperation(value = "토큰 재발급", response = ReIssueTokenResponseDto.class)
    @PostMapping("/issue/re")
    public ResponseEntity<?> reIssueToken(@RequestBody @Valid ReIssueTokenRequestDto dto) {
        ReIssueTokenResponseDto response = userService.reIssueToken(dto);
        return ResponseUtil.success(response, dto.getRequest() + " 토큰 재발급 성공");
    }


}
