package com.example.amattang.template;

import com.example.amattang.configuration.AmazonS3Config;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

@SpringBootTest(classes = {AmazonS3Template.class, AmazonS3Config.class})
class AmazonS3TemplateTest {

    @Autowired
    private AmazonS3Template amazonS3Template;

    @DisplayName("s3 이미지 업로드 테스트")
    public String putObjectTest() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("spring.png", new ClassPathResource("/test/spring.png").getInputStream());
        String fileName = amazonS3Template.converFileNameToMd5("spring.png");
        amazonS3Template.putImage(mockMultipartFile, fileName);
        return fileName;
    }

    @Test
    @DisplayName("s3 이미지 업로드에 이어 s3 이미지 삭제 테스트")
    public void deleteObjectTest() throws IOException {
        String hash = putObjectTest();
        amazonS3Template.deleteFromS3(hash);
    }
}