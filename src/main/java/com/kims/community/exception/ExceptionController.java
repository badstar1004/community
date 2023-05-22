package com.kims.community.exception;

import com.kims.community.exception.business.BusinessException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    /**
     * Valid 어노테이션 유효성 예외 처리
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(
            new ExceptionResponse(
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getCode(),
                ex.getBindingResult().getFieldError().getDefaultMessage()));
    }

    /**
     * 비즈니스 예외 처리
     * @param ex 비즈니스 예외
     * @return ExceptionResponse
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> customRequestException(final BusinessException ex) {
        return ResponseEntity.badRequest().body(
            new ExceptionResponse(ex.getErrorCode().toString(), ex.getMessage()));
    }


    @Getter
    @AllArgsConstructor
    @ToString
    public static class ExceptionResponse {

        private String errorCode;
        private String message;
    }
}
