package com.kims.community.exception.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 컨트롤러 매개변수
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, ""),

    // 회원가입
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),

    // 로그인
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "가입된 정보가 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다."),

    // 게시판
    NOT_FOUND_BOARD_ARTICLE(HttpStatus.BAD_REQUEST, "해당 게시글이 없습니다."),
    TITLE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "같은 제목의 글이 존재합니다."),
    ARTICLE_SAME_TITLE(HttpStatus.BAD_REQUEST, "같은 제목의 글입니다."),
    NOT_AUTHOR_ARTICLE(HttpStatus.BAD_REQUEST, "해당 글의 작성자가 아닙니다."),

    // 댓글
    NOT_FOUND_ARTICLE_COMMENTS(HttpStatus.BAD_REQUEST, "해당 댓글이 없습니다."),
    AUTHOR_DIFFERENT_COMMENT(HttpStatus.BAD_REQUEST, "해당 댓글의 작성자가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
