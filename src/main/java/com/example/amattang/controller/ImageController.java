package com.example.amattang.controller;

import com.example.amattang.payload.reponse.ResponseMessage;
import com.example.amattang.payload.reponse.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.amattang.payload.reponse.ResponseMessage.*;
import static com.example.amattang.payload.reponse.ResponseUtil.succes;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/check-list/{checkListId}/image")
public class ImageController {

    @ApiOperation(value = "4-1. 체크리스트에 이미지 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "image", value = "저장할 이미지 리스트(form-data) / 저장할 이미지 s3 url 리스트(json)", dataType = "MultiPartFile")
    })
    @PostMapping
    public ResponseEntity<?> saveImage(@PathVariable("checkListId") Long checkListId, @ModelAttribute List<MultipartFile> image) {

        return succes(CREATED, CREATE_CHECK_LIST_IMAGE);
    }

    @ApiOperation(value = "4-2. 체크리스트의 메인 이미지 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "imageId", value = "메인으로 등록할 이미지 아이디")
    })
    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable("checkListId") Long checkListId, @PathVariable("imageId") Long imageId) {

        return succes(CREATED, UPDATE_CHECK_LIST_MAIN_IMAGE);
    }

    @ApiOperation(value = "4-3. 체크리스트의 이미지 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "imageIds", value = "삭제할 이미지 아이디 리스트", paramType = "param")
    })
    @DeleteMapping
    public ResponseEntity<?> deleteImage(@PathVariable("checkListId") Long checkListId, @RequestParam("imageId") List<Long> imageIds) {

        return succes(OK, DELETE_CHECK_LIST_IMAGE);
    }
}
