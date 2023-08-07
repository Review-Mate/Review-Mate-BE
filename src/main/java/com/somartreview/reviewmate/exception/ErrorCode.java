package com.somartreview.reviewmate.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    REVIEW_MATE_ERROR("0001", "처리하지 못한 예외입니다."),
    RUNTIME_ERROR("0002", "알 수 없는 예외입니다.");

    private final String code;
    private final String message;
}
