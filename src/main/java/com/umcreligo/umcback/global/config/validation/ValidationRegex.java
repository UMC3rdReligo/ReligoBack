package com.umcreligo.umcback.global.config.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {

    public static boolean isRegexImgUrl(String target){//이미지 파일 형식 체크
        String regex = "(.*?)\\.(jpg|jpeg|png|bmp)$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 날짜 형식, 전화 번호 형식 등 여러 Regex 인터넷에 검색하면 나옴.
    public static boolean isRegexNickName(String target) {
        String regex = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
