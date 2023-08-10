package com.somartreview.reviewmate.config;

import com.somartreview.reviewmate.dto.ExceptionResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ReviewMateException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(DomainLogicException.class)
    public ResponseEntity<ExceptionResponse> handleDomainLogicException(DomainLogicException e) {
        log.warn(e.getErrorCode().toString() + " : " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getErrorCode().toString() + " : " + e.getMessage())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(ConstraintViolationException e) {
        log.warn(INVALID_PROPERTY_ERROR.toString() + " : " + e.getMessage());

        String messages = extractErrorMessages(e.getConstraintViolations());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(INVALID_PROPERTY_ERROR.getCode())
                        .message(INVALID_PROPERTY_ERROR.toString() + " : " + messages)
                        .build());
    }

    private String extractErrorMessages(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMismatchedInput(MethodArgumentNotValidException e) {
        log.warn(INVALID_PROPERTY_ERROR.toString() + " : " + e.getMessage());

        String messages = extractErrorMessages(e.getBindingResult().getFieldErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(INVALID_PROPERTY_ERROR.getCode())
                        .message(INVALID_PROPERTY_ERROR.toString() + " : " + messages)
                        .build());
    }

    private String extractErrorMessages(List<FieldError> fieldErrors) {
        return fieldErrors
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponse> handleMismatchedInput(Exception e) {
        log.warn(INVALID_PROPERTY_ERROR.toString() + " : " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(INVALID_PROPERTY_ERROR.getCode())
                        .message(INVALID_PROPERTY_ERROR.toString() + " : " + "잘못된 요청입니다.")
                        .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> noHandlerFoundHandle(NoHandlerFoundException e) {
        log.warn(API_NOT_FOUND_ERROR.toString() + " : " + e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .code(API_NOT_FOUND_ERROR.getCode())
                        .message(API_NOT_FOUND_ERROR.toString() + " : " + "처리할 수 없는 요청입니다.")
                        .build());
    }


    @ExceptionHandler(ReviewMateException.class)
    public ResponseEntity<ExceptionResponse> handleReviewMateException(ReviewMateException e) {
        log.warn(e.getErrorCode().toString() + " : " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getErrorCode().toString() + " : " + e.getMessage())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        log.warn(RUNTIME_ERROR.toString() + " : " + RUNTIME_ERROR.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .code(RUNTIME_ERROR.getCode())
                        .message(RUNTIME_ERROR.toString() + " : " + RUNTIME_ERROR.getMessage())
                        .build());
    }
}
