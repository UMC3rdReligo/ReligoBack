package com.umcreligo.umcback.domain.church;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.service.ChurchProvider;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/churches")
public class ChurchController {
    private final ChurchProvider churchProvider;

    @GetMapping("/{churchId}")
    public ResponseEntity<BaseResponse<Church>> findChurch(@PathVariable("churchId") Long churchId) {
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.churchProvider.findChurch(churchId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
}
