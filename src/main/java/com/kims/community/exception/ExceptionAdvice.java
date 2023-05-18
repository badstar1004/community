package com.kims.community.exception;

import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * Valid 어노테이션 유효성 예외 처리
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();
        String errMessage = result.getFieldErrors().stream()
            .map(error -> "[" + error.getField() + "]: " + error.getDefaultMessage() + "\n")
            .collect(Collectors.joining());

        return ResponseEntity.badRequest().body(errMessage);
    }
}
