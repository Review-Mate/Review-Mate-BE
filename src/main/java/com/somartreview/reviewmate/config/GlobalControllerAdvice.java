package com.somartreview.reviewmate.config;

import com.somartreview.reviewmate.dto.ExceptionResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ReviewMateException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
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
        log.warn(e.getConstraintViolations().toString());

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
        log.warn(e.getBindingResult().getFieldErrors().toString());

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
        log.error(e.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(INVALID_PROPERTY_ERROR.getCode())
                        .message(INVALID_PROPERTY_ERROR.toString() + " : " + "잘못된 요청입니다.")
                        .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> noHandlerFoundHandle(NoHandlerFoundException e) {
        log.warn(API_NOT_FOUND_ERROR.toString() + " : " + e.getMessage());
        log.error(e.toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .code(API_NOT_FOUND_ERROR.getCode())
                        .message(API_NOT_FOUND_ERROR.toString() + " : " + "처리할 수 없는 요청입니다.")
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.warn(DB_CONFLICT_ERROR.toString() + " : " + e.getMessage());
        log.warn(DB_CONFLICT_ERROR.getMessage() + " : " + e.getCause().getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ExceptionResponse.builder()
                        .code(DB_CONFLICT_ERROR.getCode())
                        .message(DB_CONFLICT_ERROR.toString() + " : " + DB_CONFLICT_ERROR.getMessage() + " : " + e.getCause().getMessage())
                        .build());
    }

    @ExceptionHandler(ReviewMateException.class)
    public ResponseEntity<ExceptionResponse> handleReviewMateException(ReviewMateException e) {
        log.warn(e.getErrorCode().toString() + " : " + e.getMessage());
        log.error(e.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getErrorCode().toString() + " : " + e.getMessage())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e, ContentCachingRequestWrapper requestWrapper) {
        log.warn(RUNTIME_ERROR.toString() + " : " + RUNTIME_ERROR.getMessage());
        logUndefinedError(e, requestWrapper);
        log.error(e.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .code(RUNTIME_ERROR.getCode())
                        .message(RUNTIME_ERROR.toString() + " : " + RUNTIME_ERROR.getMessage())
                        .build());
    }

    private void logUndefinedError(RuntimeException e, ContentCachingRequestWrapper requestWrapper) {
        String message = e.getMessage()
                + " <- "
                + makeStackTraceMessage(e)
                + toPrettyRequestString(requestWrapper);
        log.error(message);
    }

    private String makeStackTraceMessage(RuntimeException e) {
        return Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(" <- "));
    }

    private static final String NEWLINE = System.getProperty("line.separator");

    public static String toPrettyRequestString(ContentCachingRequestWrapper requestWrapper) {
        return NEWLINE + getMethodAndURI(requestWrapper)
                + getHeaders(requestWrapper)
                + getBody(requestWrapper);
    }

    private static StringBuilder getHeaders(ContentCachingRequestWrapper requestWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            stringBuilder.append(headerName).append(": ").append(requestWrapper.getHeader(headerName)).append(NEWLINE);
        }
        return stringBuilder;
    }

    private static StringBuilder getMethodAndURI(ContentCachingRequestWrapper requestWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("method: ").append(requestWrapper.getMethod()).append(NEWLINE);
        stringBuilder.append("uri: ").append(requestWrapper.getRequestURI()).append(NEWLINE);
        return stringBuilder;
    }

    private static StringBuilder getBody(ContentCachingRequestWrapper requestWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("body: ").append(NEWLINE);
        stringBuilder.append(new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
        return stringBuilder;
    }
}
