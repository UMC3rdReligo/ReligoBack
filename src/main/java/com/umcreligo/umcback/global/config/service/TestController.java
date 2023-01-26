package com.umcreligo.umcback.global.config.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class TestController {

    private S3Service s3Service;

    @PostMapping("/gallery")
    public String execWrite(@RequestParam("images") MultipartFile file ) throws IOException {
        String imgPath = s3Service.upload(file);


        return imgPath;
    }

}
