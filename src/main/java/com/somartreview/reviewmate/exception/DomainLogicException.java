package com.somartreview.reviewmate.exception;

public class DomainLogicException extends ReviewMateException {

    public DomainLogicException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DomainLogicException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
