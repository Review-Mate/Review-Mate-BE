package com.somartreview.reviewmate.domain.product;

import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_NAME_ERROR;
import static com.somartreview.reviewmate.exception.ErrorCode.TRAVEL_PRODUCT_START_TIME_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TourCourseTest {

    private TourCourse tourCourse;

    @BeforeEach
    void setup() {
        tourCourse = TourCourse.builder()
                .name("출국 비행기")
                .startTime(LocalDateTime.of(2023, 10, 18, 23, 0))
                .endTime(LocalDateTime.of(2023, 10, 19, 3, 0))
                .packageTravelProduct(new PackageTravelProduct())
                .build();
    }

    @Test
    void 패키지_여행상품의_여행코스_이름이_공백이면_안된다() {
        // given
        String name = " ";

        // when & then
        assertThatThrownBy(() -> new TourCourse(name, LocalDateTime.of(2023, 10, 18, 23, 0), LocalDateTime.of(2023, 10, 19, 3, 0), new PackageTravelProduct()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_NAME_ERROR.getMessage());
    }

    @Test
    void 패키지_여행상품의_여행코스_이름이_255자_보다_길면_안된다() {
        // given
        String name = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new TourCourse(name, LocalDateTime.of(2023, 10, 18, 23, 0), LocalDateTime.of(2023, 10, 19, 3, 0), new PackageTravelProduct()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_NAME_ERROR.getMessage());
    }

    @Test
    void 패키지_여행상품의_여행코스_종료시간이_시작시간보다_빠르면_안된다() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 18, 23, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 17, 22, 0);

        // when & then
        assertThatThrownBy(() -> new TourCourse("출국 비행기", startTime, endTime, new PackageTravelProduct()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_START_TIME_ERROR.getMessage());
    }
}