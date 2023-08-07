package com.somartreview.reviewmate.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    INVALID_PROPERTY_ERROR("9001", "잘못된 값이 입력되었습니다."),
    API_NOT_FOUND_ERROR("9002", "요청한 API가 존재하지 않습니다"),

    REVIEW_MATE_ERROR("9908", "처리하지 못한 예외입니다."),
    RUNTIME_ERROR("9909", "알 수 없는 예외입니다.");

    private final String code;
    private final String message;
}
