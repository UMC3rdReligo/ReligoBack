package com.umcreligo.umcback.domain.user;

import com.umcreligo.umcback.domain.user.dto.SignUpReq;
import com.umcreligo.umcback.domain.user.dto.UserInfoRes;
import com.umcreligo.umcback.domain.user.service.UserService;
import com.umcreligo.umcback.global.config.BaseException;
import com.umcreligo.umcback.global.config.BaseResponse;
import com.umcreligo.umcback.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.umcreligo.umcback.global.config.BaseResponseStatus.*;
import static com.umcreligo.umcback.global.config.validation.ValidationRegex.isRegexNickName;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signup(
            @RequestBody final SignUpReq signUpReq) {
        try {
            userService.signup(signUpReq);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout() {
        userService.logout();
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<BaseResponse> withDraw(){
        try{
            userService.withDraw();
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }
    @GetMapping("/info")
    public ResponseEntity<BaseResponse<UserInfoRes>> infoByUser(){
        try {
            return ResponseEntity.ok(new BaseResponse<>(this.userService.findInfoByUser()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }
    }

    @GetMapping("/checkNickName")
    public ResponseEntity<BaseResponse> checkNickname(@RequestParam(value = "nickName", required = false) String nickName){
        if(nickName==null || nickName.equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(EMPTY_USER_NICKNAME));
        }
        if(!isRegexNickName(nickName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(INVALID_NICKNAME));
        }
        //service 호출
        try{
            userService.checkNickName(nickName);
            return ResponseEntity.ok(new BaseResponse<>(SUCCESS));
        } catch(BaseException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(NICKNAME_DUPLICATE));
        }
    }



    //이건 보류
//    @PostMapping("user/refresh")
//    public ResponseEntity<LoginTokenRes> refresh(HttpServletRequest request) {
//        return ResponseEntity.ok(userService.refresh(request));
//    }

}
