package com.umcreligo.umcback.domain.image;

import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {
    private final S3Service s3Service;

    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(new BaseResponse<>(this.s3Service.upload(file)));
    }
}
