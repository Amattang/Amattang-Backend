package com.example.amattang.controller;

import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.ResponseUtil;
import com.example.amattang.service.ImageService;
import com.example.amattang.util.CurrentUser;
import com.example.amattang.util.UserPrincipal;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/check-list/{checkListId}/image")
public class ImageController {

    private final UserRepository userRepository;
    private final ImageService imageService;

    @ApiOperation(value = "4-1. 체크리스트에 이미지 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "image", value = "저장할 이미지 리스트", dataType = "form-data")
    })
    @PostMapping
    public ResponseEntity<?> saveImage(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @ModelAttribute List<MultipartFile> image) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        imageService.saveImage(user, checkListId, image);
        return ResponseUtil.success(CREATED, CREATE_CHECK_LIST_IMAGE);
    }

    @ApiOperation(value = "4-2. 체크리스트의 메인 이미지 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "imageId", value = "메인으로 등록할 이미지 아이디")
    })
    @GetMapping("/main/{imageId}")
    public ResponseEntity<?> getImage(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @PathVariable("imageId") Long imageId) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        imageService.changeMainImage(user, checkListId, imageId);
        return ResponseUtil.success(CREATED, UPDATE_CHECK_LIST_MAIN_IMAGE);
    }

    @ApiOperation(value = "4-3. 체크리스트의 이미지 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "imageId", value = "삭제할 이미지 아이디 리스트", paramType = "param")
    })
    @DeleteMapping
    public ResponseEntity<?> deleteImage(@CurrentUser UserPrincipal userPrincipal, @PathVariable("checkListId") Long checkListId, @RequestParam("imageId") List<Long> imageIds) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        imageService.deleteImage(user, checkListId, imageIds);
        return ResponseUtil.success(OK, DELETE_CHECK_LIST_IMAGE);
    }
}
