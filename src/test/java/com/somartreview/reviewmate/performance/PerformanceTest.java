package com.somartreview.reviewmate.performance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.somartreview.reviewmate.performance.api.CustomerAPIRequester.*;
import static com.somartreview.reviewmate.performance.api.PartnerAPIRequester.*;
import static com.somartreview.reviewmate.performance.api.ReservationAPIRequester.*;
import static com.somartreview.reviewmate.performance.api.ReviewAPIRequester.*;
import static com.somartreview.reviewmate.performance.api.SingleTravelProductAPIRequester.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@Disabled // Disable this from the main application test
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("performance")
public class PerformanceTest {

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    public static final long SAFE_ID = 3L;
    public static final String PARTNER_DOMAIN = "goodchoice.kr";
    public static final String PARTNER_CUSTOM_ID_POSTFIX = "_1019_12";
    public static final String CUSTOMER_PHONE_NUMBER = "02209159941";
    public static final String DELETING_PARTNER_COMPANY_DOMAIN = "goodchoice.kr";
    public static final Long DELETING_PARTNER_SELLER_ID = 2L;
    public static final Long DELETING_PARTNER_MANAGER_ID = 2L;


    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 성능을_테스트한다() {
        log.info("===== 성능 테스트 시작 =====");
        예약_API의_성능을_테스트한다();
        고객_API의_성능을_테스트한다();
        여행상품_API의_성능을_테스트한다();
        리뷰_API의_성능을_테스트한다();
    }

    void 예약_API의_성능을_테스트한다() {
        log.info("(1/5) 예약 API");

        ExtractableResponse<Response> response = 예약을_생성한다();
        String[] urls = response.header("Location").split("/");
        long reservationId = Long.parseLong(urls[urls.length - 1]);

        예약Id로_예약을_조회한다();
        고객Id와_예약목록을_조회한다();
        상품Id와_예약목록을_조회한다();
        고객Id와_상품Id로_예약목록을_조회한다();

        예약Id로_예약을_삭제한다(reservationId);
    }

    void 고객_API의_성능을_테스트한다() {
        log.info("(2/5) 고객 API");

        커스텀Id로_고객을_조회한다();
        커스텀Id로_고객_정보를_수정한다();
        커스텀Id로_고객을_삭제한다();
    }

    void 여행상품_API의_성능을_테스트한다() {
        log.info("(3/5) 여행상품 API");

        파트너ID로_단일_여행상품_목록을_조회한다();
        카테고리별_단일_여행상품_목록을_조회한다();
        단일_여행상품_정보를_수정한다();
        파트너ID로_단일_여행상품을_삭제한다();
    }

    void 리뷰_API의_성능을_테스트한다() {
        log.info("(4/5) 리뷰 API");

        ExtractableResponse<Response> response = 예약을_생성한다();
        String[] urls = response.header("Location").split("/");
        long reservationId = Long.parseLong(urls[urls.length - 1]);
        리뷰를_생성한다();
        예약Id로_예약을_삭제한다(reservationId);
        커스텀Id로_고객을_삭제한다();
        파트너ID로_단일_여행상품을_삭제한다();

        리뷰Id로_리뷰를_조회한다();
        상품의_파트너Id로_리뷰목록을_조회한다();
    }

    /*
    삭제 요청들을 테스트한다.
     */
    void 파트너_API의_성능을_테스트한다() {
        파트너사를_삭제한다();
        파트너사_관리자를_삭제한다();
        파트너사_판매자를_삭제한다();
    }
}
