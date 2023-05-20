package com.kims.community.exception.business;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    /**
     * 생성자
     * @param errorCode 에러코드
     */
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
