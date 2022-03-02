package com.example.amattang.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/check-list/{checkListId}/image")
public class ImageController {

    @ApiOperation(value = "4-1. 체크리스트에 이미지 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "image", value = "저장할 이미지 리스트(form-data) / 저장할 이미지 s3 url 리스트(json)", dataType = "MultiPartFile")
    })
    @PostMapping
    public void saveImage(@PathVariable("checkListId") Long checkListId, @ModelAttribute List<MultipartFile> image) {

    }

    @ApiOperation(value = "4-2. 체크리스트의 메인 이미지 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "imageId", value = "메인으로 등록할 이미지 아이디")
    })
    @GetMapping("/{imageId}")
    public void getImage(@PathVariable("checkListId") Long checkListId, @PathVariable("imageId") Long imageId) {

    }

    @ApiOperation(value = "4-3. 체크리스트의 이미지 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkListId", value = "이미지를 저장할 체크리스트 아이디", paramType = "path"),
            @ApiImplicitParam(name = "imageIds", value = "삭제할 이미지 아이디 리스트", paramType = "param")
    })
    @DeleteMapping
    public void deleteImage(@PathVariable("checkListId") Long checkListId, @RequestParam("imageId") List<Long> imageIds) {

    }
}
