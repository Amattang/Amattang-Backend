package com.example.amattang.template;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AmazonS3Template {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String saveToS3(MultipartFile file) throws IOException {
        String hash = converFileNameToMd5(file.getOriginalFilename());
        return putImage(file, hash);
    }

    public String putImage(MultipartFile file, String name) throws IOException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, name, file.getInputStream(), null);
        amazonS3Client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, name).toString();
    }

    public void deleteFromS3(String name) {
        amazonS3Client.deleteObject(bucket, name);
    }

    public String converFileNameToMd5(String name) {
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
