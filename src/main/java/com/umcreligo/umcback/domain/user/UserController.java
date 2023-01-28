package com.umcreligo.umcback.domain.user;

import com.umcreligo.umcback.domain.user.dto.LoginTokenRes;
import com.umcreligo.umcback.domain.user.dto.SignUpReq;
import com.umcreligo.umcback.domain.user.dto.UserChurchRes;
import com.umcreligo.umcback.domain.user.service.UserService;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public BaseResponse signup(
            @RequestBody final SignUpReq signUpReq) {
        userService.signup(signUpReq);
        System.out.println("성공");
        return new BaseResponse(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/user/logout")
    public BaseResponse logout() {
        userService.logout();
        return new BaseResponse(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/user/church")
    public ResponseEntity<BaseResponse<UserChurchRes>> ChurchbyUser(){
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.userService.findChurchbyUser()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    //이건 보류
//    @PostMapping("user/refresh")
//    public ResponseEntity<LoginTokenRes> refresh(HttpServletRequest request) {
//        return ResponseEntity.ok(userService.refresh(request));
//    }

}
