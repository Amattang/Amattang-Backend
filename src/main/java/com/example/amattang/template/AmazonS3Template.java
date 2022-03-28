package com.example.amattang.template;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Template {

    @Getter
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String saveToS3(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String hash = convertFileNameToMd5(originalFilename);
            return putImage(file, hash + originalFilename.substring(originalFilename.lastIndexOf(".")));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public String putImage(MultipartFile file, String name) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, name, file.getInputStream(), objectMetadata);
        amazonS3Client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, name).toString();
    }

    public void deleteFromS3(String name) {
        amazonS3Client.deleteObject(bucket, name);
    }

    public String convertFileNameToMd5(String name) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            name = name + new Date();
            md.update(name.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : digest) {
                String format = String.format("%02x", b);
                stringBuilder.append(format);
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new NullPointerException();
        }
    }

}
