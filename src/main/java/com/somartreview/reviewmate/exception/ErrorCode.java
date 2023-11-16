package com.somartreview.reviewmate.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    CUSTOMER_NAME_ERROR("1000", "고객의 이름이 너무 길거나 공백입니다."),
    CUSTOMER_PHONE_NUMBER_ERROR("1001", "고객의 전화번호가 기호를 포함하거나 공백입니다."),
    CUSTOMER_PARTNER_CUSTOM_ID_ERROR("1002", "고객의 파트너 커스텀 ID가 너무 길거나 공백입니다."),
    CUSTOMER_PARTNER_DOMAIN_ERROR("1003", "고객의 파트너 도메인이 너무 길거나 공백입니다."),
    CUSTOMER_NOT_UNIQUE_PARTNER_CUSTOM_ID("1004", "고객의 파트너 커스텀 ID가 유니크하지 않습니다."),
    CUSTOMER_NOT_FOUND("1005", "고객을 찾을 수 없습니다."),
    CUSTOMER_NOT_UNIQUE_PHONE_NUMBER("1006", "고객의 전화번호가 유니크하지 않습니다."),
    CUSTOMER_NOT_UNIQUE_KAKAO_ID("1007", "고객의 카카오 ID가 유니크하지 않습니다."),

    LIVE_FEEDBACK_MESSAGE_ERROR("1100", "실시간 피드백의 메시지가 너무 길거나 공백입니다."),
    LIVE_FEEDBACK_MEDIA_URL_ERROR("1101", "실시간 피드백의 미디어 링크가 너무 길거나 공백입니다."),
    LIVE_FEEDBACK_NOT_HANDLED_YET("1102", "아직 처리되지 않은 실시간 피드백입니다."),
    LIVE_FEEDBACK_ALREADY_REPORTED_ERROR("1103", "이미 신고된 실시간 피드백입니다."),
    LIVE_FEEDBACK_ALREADY_SOLVED_ERROR("1104", "이미 해결된 실시간 피드백입니다."),
    LIVE_FEEDBACK_NOT_FOUND("1105", "실시간 피드백을 찾을 수 없습니다."),

    LIVE_SATISFACTION_RATING_ERROR("1110", "실시간 만족도의 별점이 잘못된 범위입니다."),
    LIVE_SATISFACTION_NOT_FOUND("1111", "실시간 만족도를 찾을 수 없습니다."),

    PARTNER_COMPANY_NAME_ERROR("1200", "파트너사의 이름이 너무 길거나 공백입니다."),
    PARTNER_COMPANY_NOT_FOUND("1201", "파트너사를 찾을 수 없습니다."),
    PARTNER_COMPANY_NOT_UNIQUE_PARTNER_DOMAIN("1202", "파트너사의 도메인이 유니크하지 않습니다."),
    PARTNER_COMPANY_DUPLICATED_PARTNER_DOMAIN("1202", "파트너사의 도메인이 이미 존재합니다."),

    PARTNER_MANAGER_NAME_ERROR("1210", "관리자의 이름이 너무 길거나 공백입니다."),
    PARTNER_MANAGER_EMAIL_ERROR("1211", "관리자의 이메일이 올바른 형식이 아닙니다."),
    PARTNER_MANAGER_PASSWORD_ERROR("1212", "관리자의 비밀번호가 너무 짧거나 공백입니다."),
    PARTNER_MANAGER_NOT_FOUND("1213", "관리자를 찾을 수 없습니다."),

    PARTNER_SELLER_NAME_ERROR("1220", "판매자의 이름이 너무 길거나 공백입니다."),
    PARTNER_SELLER_EMAIL_ERROR("1221", "판매자의 이메일이 올바른 형식이 아닙니다."),
    PARTNER_SELLER_PASSWORD_ERROR("1222", "판매자의 비밀번호가 너무 짧거나 공백입니다."),
    PARTNER_SELLER_NOT_FOUND("1223", "판매자를 찾을 수 없습니다."),
    PARTNER_SELLER_NOT_UNIQUE_PHONE_NUMBER("1224", "판매자의 전화번호가 유니크하지 않습니다."),
    PARTNER_SELLER_NOT_UNIQUE_KAKAO_ID("1225", "판매자의 카카오 ID가 유니크하지 않습니다."),

    REVIEW_RATING_ERROR("1300", "리뷰의 별점이 잘못된 범위입니다."),
    REVIEW_TITLE_ERROR("1301", "리뷰의 제목이 너무 길거나 공백입니다."),
    REVIEW_CONTENT_ERROR("1302", "리뷰의 내용이 너무 길거나 공백입니다."),
    REVIEW_NOT_FOUND("1303", "리뷰를 찾을 수 없습니다."),
    REVIEW_ALREADY_EXISTS_ON_RESERVATION("1304", "해당 예약에 이미 리뷰가 작성됐습니다."),

    REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR("1310", "리뷰 태그의 인덱스가 범위가 벗어났습니다."),
    REVIEW_TAG_FRONT_END_INDEX_ERROR("1311", "리뷰 태그의 끝 인덱스가 시작 인덱스보다 앞에 있습니다."),

    REVIEW_IMAGE_URL_ERROR("1320", "리뷰 이미지의 링크가 너무 길거나 공백입니다."),
    REVIEW_IMAGE_FILE_IO_ERROR("1321", "리뷰 이미지의 IO 과정에서 오류가 발생했습니다."),

    TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR("1400", "여행상품의 썸네일 링크가 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_NAME_ERROR("1401", "여행상품의 이름이 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_PARTNER_CUSTOM_ID_ERROR("1402", "여행상품의 파트너 커스텀 ID가 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_PARTNER_DOMAIN_ERROR("1403", "여행상품의 파트너 도메인이 너무 길거나 공백입니다."),
    TRAVEL_PRODUCT_NOT_FOUND("1404", "여행상품을 찾을 수 없습니다."),
    TRAVEL_PRODUCT_NOT_UNIQUE_PARTNER_CUSTOM_ID("1405", "여행상품의 파트너 커스텀 ID가 유니크하지 않습니다."),

    RESERVATION_NOT_FOUND("1500", "예약을 찾을 수 없습니다."),
    RESERVATION_PARTNER_CUSTOM_ID_ERROR("1501", "예약의 파트너 커스텀 ID가 너무 길거나 공백입니다."),
    RESERVATION_NOT_UNIQUE_PARTNER_CUSTOM_ID("1502", "예약의 파트너 커스텀 ID가 유니크하지 않습니다."),
    RESERVATION_FRONT_END_TIME_ERROR("1402", "여행상품의 종료 시간이 시작 시간보다 빠를 수 없습니다."),


    NOT_EXIST_PARTNER_DOMAIN("2001", "존재하지 않는 파트너사 도메인입니다."),


    INVALID_PROPERTY_ERROR("9001", "잘못된 값이 입력되었습니다."),
    MISSED_PARAMETER_ERROR("9002", "필수 파라미터가 누락되었습니다."),
    API_NOT_FOUND_ERROR("9006", "요청한 API가 존재하지 않습니다"),
    DB_CONFLICT_ERROR("9007", "요청한 데이터가 데이터베이스와 충돌됩니다."),
    REVIEW_MATE_ERROR("9908", "처리하지 못한 예외입니다."),

    SCHEDULER_REGISTER_ERROR("9101", "예약 스케줄러 등록에 실패했습니다."),

    AWS_S3_CLIENT_ERROR("9801", "AWS S3 Client 에러입니다."),

    RUNTIME_ERROR("9909", "알 수 없는 예외입니다.");

    private final String code;
    private final String message;
}
