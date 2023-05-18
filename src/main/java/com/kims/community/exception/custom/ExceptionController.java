package com.kims.community.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customRequestException(final CustomException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getErrorCode(),
            ex.getMessage()));
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class ExceptionResponse {
        private ErrorCode errorCode;
        private String message;
    }
}
