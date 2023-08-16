package com.somartreview.reviewmate.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    CUSTOMER_NAME_ERROR("1000", "고객의 이름이 너무 길거나 공백입니다."),
    CUSTOMER_PHONE_NUMBER_ERROR("1001", "고객의 전화번호가 기호를 포함하거나 공백입니다."),
    CUSTOMER_PARTNER_ID_ERROR("1002", "고객의 파트너 ID가 너무 길거나 공백입니다."),
    CUSTOMER_DUPLICATED_PARTNER_ID("1003", "고객의 클라이언트 사 ID가 중복됩니다."),
    CUSTOMER_NOT_FOUND("1004", "고객을 찾을 수 없습니다."),

    LIVE_FEEDBACK_MESSAGE_ERROR("1100", "실시간 피드백의 메시지가 너무 길거나 공백입니다."),
    LIVE_FEEDBACK_MEDIA_URL_ERROR("1101", "실시간 피드백의 미디어 링크가 너무 길거나 공백입니다."),
    LIVE_FEEDBACK_ALREADY_REPORTED_ERROR("1102", "이미 신고된 실시간 피드백입니다."),
    LIVE_FEEDBACK_ALREADY_HANDLED_ERROR("1103", "이미 해결된 실시간 피드백입니다."),
    LIVE_FEEDBACK_ALREADY_SOLVED_ERROR("1104", "이미 해결된 실시간 피드백입니다."),

    LIVE_SATISFACTION_RATING_ERROR("1110", "실시간 만족도의 별점이 잘못된 범위입니다."),

    PARTNER_COMPANY_NAME_ERROR("1200", "파트너사의 이름이 너무 길거나 공백입니다."),
    PARTNER_COMPANY_NOT_FOUND("1201", "파트너사를 찾을 수 없습니다."),

    PARTNER_MANAGER_NAME_ERROR("1210", "파트너사 관리자의 이름이 너무 길거나 공백입니다."),
    PARTNER_MANAGER_EMAIL_ERROR("1211", "파트너사 관리자의 이메일이 올바른 형식이 아닙니다."),
    PARTNER_MANAGER_PASSWORD_ERROR("1212", "파트너사 관리자의 비밀번호가 너무 짧거나 공백입니다."),

    PARTNER_SELLER_NAME_ERROR("1220", "파트너사 판매자의 이름이 너무 길거나 공백입니다."),
    PARTNER_SELLER_EMAIL_ERROR("1221", "파트너사 판매자의 이메일이 올바른 형식이 아닙니다."),
    PARTNER_SELLER_PASSWORD_ERROR("1222", "파트너사 판매자의 비밀번호가 너무 짧거나 공백입니다."),
    PARTNER_SELLER_NOT_FOUND("1223", "파트너사 판매자를 찾을 수 없습니다."),

    REVIEW_TITLE_ERROR("1300", "리뷰의 제목이 너무 길거나 공백입니다."),
    REVIEW_CONTENT_ERROR("1301", "리뷰의 내용이 너무 길거나 공백입니다."),

    REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR("1310", "리뷰 태그의 인덱스가 범위가 벗어났습니다."),

    REVIEW_IMAGE_URL_ERROR("1320", "리뷰 이미지의 링크가 너무 길거나 공백입니다."),

    TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR("1400", "여행 상품의 썸네일 링크가 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_NAME_ERROR("1401", "여행 상품의 이름이 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_START_TIME_ERROR("1402", "여행 상품의 시작 시간이 종료 시간 이후일 수 없습니다.."),
    TRAVEL_PRODUCT_PARTNER_ID_ERROR("1403", "여행 상품의 파트너 ID가 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_NOT_FOUND("1404", "여행 상품을 찾을 수 없습니다."),
    TRAVEL_PRODUCT_DUPLICATED_PARTNER_ID("1405", "여행 상품의 파트너 ID가 중복됩니다."),

    INVALID_PROPERTY_ERROR("9001", "잘못된 값이 입력되었습니다."),
    API_NOT_FOUND_ERROR("9002", "요청한 API가 존재하지 않습니다"),
    DB_CONFLICT_ERROR("9003", "요청한 데이터가 데이터베이스와 충돌됩니다."),

    REVIEW_MATE_ERROR("9908", "처리하지 못한 예외입니다."),
    RUNTIME_ERROR("9909", "알 수 없는 예외입니다.");

    private final String code;
    private final String message;
}
