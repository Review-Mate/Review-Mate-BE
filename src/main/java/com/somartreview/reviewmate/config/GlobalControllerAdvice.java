package com.somartreview.reviewmate.config;

import com.somartreview.reviewmate.dto.ExceptionResponse;
import com.somartreview.reviewmate.exception.ReviewMateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

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
