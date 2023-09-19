package com.somartreview.reviewmate.performance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.somartreview.reviewmate.performance.api.ReservationApi.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("performance")
public class PerformanceTest {

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    public static final String PARTNER_DOMAIN = "goodchoice.kr";
    public static final long NEW_RESERVATION_ID = 500000L;
    public static final String PARTNER_CUSTOMER_ID_POSTFIX = "_1018_1";

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
    }

    void 예약_API의_성능을_테스트한다() {
        예약을_생성한다();
        예약Id로_예약을_조회한다();
        고객Id와_예약목록을_조회한다();
        상품Id와_예약목록을_조회한다();
        고객Id와_상품Id로_예약목록을_조회한다();
        예약Id로_예약을_삭제한다();
    }
}
