package com.somartreview.reviewmate.exception;

import lombok.Getter;

@Getter
public class ReviewMateException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReviewMateException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + ", " + message);
        this.errorCode = errorCode;
    }
}
