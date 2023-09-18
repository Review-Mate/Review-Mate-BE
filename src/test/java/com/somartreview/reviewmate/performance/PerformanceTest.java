package com.somartreview.reviewmate.performance;

import com.somartreview.reviewmate.domain.partner.manager.PartnerManager;
import com.somartreview.reviewmate.domain.review.ReviewPolarity;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.performance.dao.LiveDao;
import com.somartreview.reviewmate.performance.dao.PartnerDao;
import com.somartreview.reviewmate.performance.dao.ProductDao;
import com.somartreview.reviewmate.performance.dao.ReviewDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.somartreview.reviewmate.performance.api.ReservationApiRequest.예약Id로_예약을_조회한다;
import static com.somartreview.reviewmate.performance.api.ReservationApiRequest.예약을_생성한다;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("performance")
class PerformanceTest {

//    private static final int COMPANIES_SIZE = 2;
//    private static final int MANAGERS_SIZE = 10;
//    private static final int SELLERS_SIZE = 20;
//    private static final int PRODUCTS_SIZE = 20;
//    private static final int CUSTOMERS_SIZE = 10;
//    private static final int RESERVATIONS_SIZE = 50;
//    private static final int REVIEWS_SIZE = 50;
//    private static final int REVIEW_TAGS_SIZE = 250;
//    private static final int REVIEW_IMAGES_SIZE = 100;
//    private static final int LIVE_SATISFACTIONS_SIZE = 50;
//    private static final int LIVE_FEEDBACKS_SIZE = 50;

    private static final int COMPANIES_SIZE = 1;
    private static final int MANAGERS_SIZE = 10;
    private static final int SELLERS_SIZE = 1_000;
    private static final int PRODUCTS_SIZE = 1_000;
    private static final int CUSTOMERS_SIZE = 5_000;
    private static final int RESERVATIONS_SIZE = 200_000;
    private static final int REVIEWS_SIZE = 200_000;
    private static final int REVIEW_TAGS_SIZE = 1_000_000;
    private static final int REVIEW_IMAGES_SIZE = 400_000;
    private static final int LIVE_SATISFACTIONS_SIZE = 200_000;
    private static final int LIVE_FEEDBACKS_SIZE = 200_000;

//    private static final int COMPANIES_SIZE = 2;
//    private static final int MANAGERS_SIZE = 20;
//    private static final int SELLERS_SIZE = 2_000;
//    private static final int PRODUCTS_SIZE = 2_000;
//    private static final int CUSTOMERS_SIZE = 10_000;
//    private static final int RESERVATIONS_SIZE = 400_000;
//    private static final int REVIEWS_SIZE = 400_000;
//    private static final int REVIEW_TAGS_SIZE = 2_000_000;
//    private static final int REVIEW_IMAGES_SIZE = 800_000;
//    private static final int LIVE_SATISFACTIONS_SIZE = 400_000;
//    private static final int LIVE_FEEDBACKS_SIZE = 400_000;

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    @Autowired
    private PartnerDao partnerDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private LiveDao liveDao;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        더미데이터를_추가한다();
    }

    private void 더미데이터를_추가한다() {
        log.info("===== 데미데이터 추가 시작 =====");
        long startTime = System.currentTimeMillis();

        long[] companyIds = LongStream.range(1, COMPANIES_SIZE + 1).toArray();
        long[] sellerIds = LongStream.range(1, SELLERS_SIZE + 1).toArray();
        long[] customerIds = LongStream.range(1, CUSTOMERS_SIZE + 1).toArray();
        long[] travelProductIds = LongStream.range(1, PRODUCTS_SIZE + 1).toArray();
        long[] reservationIds = LongStream.range(1, RESERVATIONS_SIZE + 1).toArray();
        long[] reviewIds = LongStream.range(1, REVIEWS_SIZE + 1).toArray();


        log.info("(1/11) INSERT INTO PARTNER_COMPANIES");
        partnerDao.batchInsertPartnerCompanies(COMPANIES_SIZE);
        log.info("(2/11) INSERT INTO PARTNER_MANAGERS");
        partnerDao.batchInsertPartnerManagers(companyIds, MANAGERS_SIZE);
        log.info("(3/11) INSERT INTO PARTNER_SELLERS");
        partnerDao.batchInsertPartnerSellers(companyIds, SELLERS_SIZE);

        log.info("(4/11) INSERT INTO SINGLE_TRAVEL_PRODUCTS");
        productDao.batchInsertSingleTravelProduct(companyIds, sellerIds, PRODUCTS_SIZE, REVIEWS_SIZE);
        log.info("(5/11) INSERT INTO CUSTOMERS");
        productDao.batchInsertCustomers(companyIds, CUSTOMERS_SIZE);

        log.info("(6/11) INSERT INTO RESERVATIONS");
        reviewDao.batchInsertReservations(travelProductIds, customerIds, RESERVATIONS_SIZE);
        log.info("(7/11) INSERT INTO REVIEWS");
        reviewDao.batchInsertReviews(reservationIds, REVIEWS_SIZE);
        log.info("(8/11) INSERT INTO REVIEW_TAGS");
        reviewDao.batchInsertReviewTags(reviewIds, REVIEW_TAGS_SIZE);
        log.info("(9/11) INSERT INTO REVIEW_IMAGES");
        reviewDao.batchInsertReviewImages(reviewIds, REVIEW_IMAGES_SIZE);

        log.info("(10/11) INSERT INTO LIVE_FEEDBACKS");
        liveDao.batchInsertLiveFeedbacks(reservationIds, LIVE_FEEDBACKS_SIZE);
        log.info("(11/11) INSERT INTO LIVE_SATISFACTIONS");
        liveDao.batchInsertLiveSatisfactions(reservationIds, LIVE_SATISFACTIONS_SIZE);


        double dummyQueryTime = (System.currentTimeMillis() - startTime) / 1000.0;
        log.info("===== 데미데이터 추가 종료 =====");
        log.info("더미데이터 추가 시간: {} 초", dummyQueryTime);
    }

    @Test
    void 성능을_테스트한다() {
        log.info("===== 성능 테스트 시작 =====");
        예약_API의_성능을_테스트한다();
    }

    void 예약_API의_성능을_테스트한다() {
        예약을_생성한다();
        예약Id로_예약을_조회한다();
    }
}
