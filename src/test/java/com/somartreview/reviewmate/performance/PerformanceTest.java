package com.somartreview.reviewmate.performance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.somartreview.reviewmate.performance.api.CustomerAPIRequestTest.*;
import static com.somartreview.reviewmate.performance.api.ReservationAPIRequestTest.*;
import static com.somartreview.reviewmate.performance.api.SingleTravelProductAPIRequestTest.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("performance")
public class PerformanceTest {

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    public static final String PARTNER_DOMAIN = "goodchoice.kr";
    public static final long SAFE_ID = 5L;
    public static final String PARTNER_CUSTOM_ID_POSTFIX = "_1018_5";

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
    }

    void 예약_API의_성능을_테스트한다() {
        ExtractableResponse<Response> response = 예약을_생성한다();
        String[] urls = response.header("Location").split("/");
        long reservationId = Long.parseLong(urls[urls.length - 1]);

        예약Id로_예약을_조회한다();
        고객Id와_상품Id로_예약목록을_조회한다();

        예약Id로_예약을_삭제한다(reservationId);
    }

    void 고객_API의_성능을_테스트한다() {
        커스텀Id로_고객을_조회한다();
        커스텀Id로_고객_정보를_수정한다();
        커스텀Id로_고객을_삭제한다();
    }

    void 여행상품_API의_성능을_테스트한다() {
        파트너ID로_단일_여행상품_목록을_조회한다();
        카테고리별_단일_여행상품_목록을_조회한다();
        단일_여행상품_정보를_수정한다();
        파트너ID로_단일_여행상품을_삭제한다();
    }
}
