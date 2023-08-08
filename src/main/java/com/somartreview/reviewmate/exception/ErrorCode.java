package com.somartreview.reviewmate.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    CUSTOMER_NAME_ERROR("1001", "고객의 이름이 너무 길거나 공백입니다."),
    CUSTOMER_PHONE_NUMBER_ERROR("1002", "고객의 전화번호가 기호를 포함하거나 공백입니다."),

    LIVE_FEEDBACK_MESSAGE_ERROR("1011", "실시간 피드백의 메시지가 너무 길거나 공백입니다."),
    LIVE_FEEDBACK_MEDIA_URL_ERROR("1012", "실시간 피드백의 미디어 링크가 너무 길거나 공백입니다."),
    LIVE_FEEDBACK_ALREADY_REPORTED_ERROR("1013", "이미 신고된 실시간 피드백입니다."),
    LIVE_FEEDBACK_ALREADY_HANDLED_ERROR("1014", "이미 해결된 실시간 피드백입니다."),
    LIVE_FEEDBACK_ALREADY_SOLVED_ERROR("1015", "이미 해결된 실시간 피드백입니다."),

    LIVE_SATISFACTION_RATING_ERROR("1021", "실시간 만족도의 별점이 잘못된 범위입니다."),

    PARTNER_COMPANY_NAME_ERROR("1031", "파트너사의 이름이 너무 길거나 공백입니다."),

    PARTNER_MANAGER_NAME_ERROR("1041", "파트너사 관리자의 이름이 너무 길거나 공백입니다."),
    PARTNER_MANAGER_EMAIL_ERROR("1042", "파트너사 관리자의 이메일이 올바른 형식이 아닙니다."),
    PARTNER_MANAGER_PASSWORD_ERROR("1043", "파트너사 관리자의 비밀번호가 너무 짧거나 공백입니다."),

    PARTNER_SELLER_NAME_ERROR("1051", "파트너사 판매자의 이름이 너무 길거나 공백입니다."),
    PARTNER_SELLER_EMAIL_ERROR("1052", "파트너사 판매자의 이메일이 올바른 형식이 아닙니다."),
    PARTNER_SELLER_PASSWORD_ERROR("1053", "파트너사 판매자의 비밀번호가 너무 짧거나 공백입니다."),

    INVALID_PROPERTY_ERROR("9001", "잘못된 값이 입력되었습니다."),
    API_NOT_FOUND_ERROR("9002", "요청한 API가 존재하지 않습니다"),

    REVIEW_MATE_ERROR("9908", "처리하지 못한 예외입니다."),
    RUNTIME_ERROR("9909", "알 수 없는 예외입니다.");

    private final String code;
    private final String message;
}
