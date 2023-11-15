package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.service.review.WidgetReviewSearchCond;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static com.somartreview.reviewmate.domain.review.ReviewOrderCriteria.*;
import static com.somartreview.reviewmate.domain.review.ReviewProperty.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;

@DataJpaTest
@ActiveProfiles("data")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use the Datasource defined in application.yml
class ReviewCustomRepositoryTest {

//    @Autowired
//    private ReviewRepository reviewRepository;
//
//
//    @Test
//    void 위젯_리뷰를_속성으로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(ROOM, null, null);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results).hasSize(2);
//
//        for (Review review : results.get().toList()) {
//            assertThat(review.getReviewTags())
//                    .extracting(ReviewTag::getReviewProperty)
//                    .contains(ROOM);
//        }
//    }
//
//    @Test
//    void 위젯_리뷰를_키워드로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(ROOM, "먼지", null);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results).hasSize(1);
//
//        for (Review review : results.get().toList()) {
//            assertThat(review.getReviewTags())
//                    .extracting(ReviewTag::getReviewProperty, ReviewTag::getKeyword)
//                    .contains(tuple(ROOM, "먼지"));
//        }
//    }
//
//    @Test
//    void 위젯_리뷰를_최신순으로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(null, null, LATEST);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results)
//                .extracting(Review::getId)
//                .containsExactly(3L, 2L, 1L);
//    }
//
//    @Test
//    void 위젯_리뷰를_별점높은순으로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(null, null, RATING_DESC);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results)
//                .extracting(Review::getId)
//                .containsExactly(2L, 3L, 1L);
//    }
//
//    @Test
//    void 위젯_리뷰를_별점낮은순으로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(null, null, RATING_ASC);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results)
//                .extracting(Review::getId)
//                .containsExactly(1L, 3L, 2L);
//    }
//
//    @Test
//    void 위젯_리뷰를_긍정적인순으로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(null, null, POSITIVE_DESC);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results)
//                .extracting(Review::getId)
//                .containsExactly(3L, 1L, 2L);
//    }
//
//    @Test
//    void 위젯_리뷰를_부정적인순으로_검색한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(null, null, NEGATIVE_DESC);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(0, 10)
//        );
//
//        // then
//        assertThat(results)
//                .extracting(Review::getId)
//                .containsExactly(2L, 3L, 1L);
//    }
//
//    @Test
//    void 위젯_리뷰를_페이징한다() {
//        // given
//        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(null, null, null);
//
//        // when
//        Page<Review> results = reviewRepository.searchWidgetReviews(
//                "goodchoice.kr",
//                "HOTEL_0001",
//                widgetReviewSearchCond,
//                PageRequest.of(2, 1)
//        );
//
//        // then
//        assertThat(results).hasSize(1);
//        assertThat(results.get().toList().get(0).getId()).isEqualTo(1L);
//    }
}
