package com.umcreligo.umcback.domain;

import com.umcreligo.umcback.global.config.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class TestController {

    private S3Service s3Service;

    @PostMapping("/gallery")
    public String execWrite(@RequestPart("file") MultipartFile file ) throws IOException {
        String imgPath = s3Service.upload(file);


        return imgPath;
    }

}
