package com.kims.community.exception.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원가입
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),

    // 로그인
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "가입된 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}