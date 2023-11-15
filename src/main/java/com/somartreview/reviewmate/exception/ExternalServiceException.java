package com.somartreview.reviewmate.exception;

import lombok.Getter;

@Getter
public class ExternalServiceException extends ReviewMateException {

    public ExternalServiceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExternalServiceException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}