package com.somartreview.reviewmate.performance;

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

import java.util.concurrent.ThreadLocalRandom;

import static com.somartreview.reviewmate.performance.api.ReservationApiRequest.예약Id로_예약을_조회한다;
import static com.somartreview.reviewmate.performance.api.ReservationApiRequest.예약을_생성한다;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("performance")
class PerformanceTest {

    private static final int COMPANIES_SIZE = 2;
    private static final int MANAGERS_PER_COMPANY_SIZE = 10;
    private static final int SELLERS_PER_COMPANY_SIZE = 50;
    private static final int PRODUCTS_PER_SELLER_SIZE = 1;
    //    private static final int CUSTOMERS_PER_COMPANY_SIZE = 5000;
    private static final int CUSTOMERS_PER_COMPANY_SIZE = 1000;
    private static final int RESERVATIONS_PER_CUSTOMER_SIZE = 5;
    private static final int REVIEWS_PER_RESERVATION_SIZE = 1;
    private static final int REVIEW_TAGS_PER_REVIEW_SIZE = 5;
    private static final int REVIEW_IMAGES_PER_REVIEW_SIZE = 2;
    private static final int LIVE_SATISFACTIONS_PER_RESERVATION = 1;
    private static final int LIVE_FEEDBACKS_PER_RESERVATION = 1;

//    private static final int COMPANIES_SIZE = 1;
//    private static final int MANAGERS_PER_COMPANY_SIZE = 1;
//    private static final int SELLERS_PER_COMPANY_SIZE = 1;
//    private static final int PRODUCTS_PER_SELLER_SIZE = 1;
//    private static final int CUSTOMERS_PER_COMPANY_SIZE = 1;
//    private static final int RESERVATIONS_PER_CUSTOMER_SIZE = 1;
//    private static final int REVIEWS_PER_RESERVATION_SIZE = 1;
//    private static final int REVIEW_TAGS_PER_REVIEW_SIZE = 1;
//    private static final int REVIEW_IMAGES_PER_REVIEW_SIZE = 1;
//    private static final int LIVE_SATISFACTIONS_PER_RESERVATION = 1;
//    private static final int LIVE_FEEDBACKS_PER_RESERVATION = 1;

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

        long sellerIdIdx = 1;
        long customerIdIdx = 1;
        long travelProductIdLowerIdx = 1;
        long travelProductIdUpperIdx = 1;
        long reservationIdIdx = 1;
        long reviewIdIdx = 1;

        partnerDao.batchInsertPartnerCompanies(COMPANIES_SIZE);
        for (long companyId = 1; companyId <= COMPANIES_SIZE; companyId++) {
            partnerDao.batchInsertPartnerManagers(companyId, MANAGERS_PER_COMPANY_SIZE);
            partnerDao.batchInsertPartnerSellers(companyId, SELLERS_PER_COMPANY_SIZE);

            for (long sellerId = sellerIdIdx; sellerId < sellerIdIdx + SELLERS_PER_COMPANY_SIZE; sellerId++) {
                productDao.batchInsertSingleTravelProduct(sellerId, companyId, PRODUCTS_PER_SELLER_SIZE, PRODUCTS_PER_SELLER_SIZE);
            }
            sellerIdIdx += SELLERS_PER_COMPANY_SIZE;
            travelProductIdLowerIdx = travelProductIdUpperIdx;
            travelProductIdUpperIdx += PRODUCTS_PER_SELLER_SIZE;

            productDao.batchInsertCustomers(companyId, CUSTOMERS_PER_COMPANY_SIZE);

            for (long customerId = customerIdIdx; customerId < customerIdIdx + CUSTOMERS_PER_COMPANY_SIZE; customerId++) {
                reviewDao.batchInsertReservations(customerId, ThreadLocalRandom.current().nextLong(travelProductIdLowerIdx, travelProductIdUpperIdx), RESERVATIONS_PER_CUSTOMER_SIZE);

                for (long reservationId = reservationIdIdx; reservationId < reservationIdIdx + RESERVATIONS_PER_CUSTOMER_SIZE; reservationId++) {
                    reviewDao.batchInsertReviews(reservationId, REVIEWS_PER_RESERVATION_SIZE);
                    reviewDao.batchInsertReviewTags(reviewIdIdx, REVIEW_TAGS_PER_REVIEW_SIZE);
                    reviewDao.batchInsertReviewImages(reviewIdIdx, REVIEW_IMAGES_PER_REVIEW_SIZE);
                    liveDao.batchInsertLiveFeedbacks(reservationId, LIVE_FEEDBACKS_PER_RESERVATION);
                    liveDao.batchInsertLiveSatisfactions(reservationId, LIVE_SATISFACTIONS_PER_RESERVATION);

                    reviewIdIdx++;
                }
                reservationIdIdx += RESERVATIONS_PER_CUSTOMER_SIZE;
            }
            customerIdIdx += CUSTOMERS_PER_COMPANY_SIZE;
        }

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
