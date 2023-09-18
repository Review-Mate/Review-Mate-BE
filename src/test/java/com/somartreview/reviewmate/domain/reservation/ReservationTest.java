package com.somartreview.reviewmate.domain.reservation;


import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setup() {
        reservation = Reservation.builder()
                .partnerCustomId("RESERVATION_0001")
                .startDateTime(LocalDateTime.of(2023, 10, 18, 13, 0))
                .endDateTime(LocalDateTime.of(2023, 10, 19, 12, 0))
                .customer(new Customer())
                .travelProduct(new SingleTravelProduct())
                .build();
    }

    @Test
    void 예약을_생성한다() {
        // when
        Reservation reservation = new Reservation("RESERVATION_0001", LocalDateTime.of(2023, 10, 18, 13, 0), LocalDateTime.of(2023, 10, 19, 12, 0), new Customer(), new SingleTravelProduct());

        // then
        assertThat(reservation)
                .extracting("partnerCustomId", "startDateTime", "endDateTime")
                .containsExactly("RESERVATION_0001", LocalDateTime.of(2023, 10, 18, 13, 0), LocalDateTime.of(2023, 10, 19, 12, 0));
        assertThat(reservation.getCustomer()).isNotNull();
        assertThat(reservation.getTravelProduct()).isNotNull();
    }

    @Test
    void 예약의_파트너_커스텀_Id가_공백이면_안된다() {
        // given
        String partnerCustomId = " ";

        LocalDateTime startDateTime = LocalDateTime.of(2023, 10, 18, 13, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 10, 19, 12, 0);
        Customer mockCustomer = new Customer();
        SingleTravelProduct mockSingleTravelProduct = new SingleTravelProduct();

        // when & then
        assertThatThrownBy(() -> new Reservation(partnerCustomId, startDateTime, endDateTime, mockCustomer, mockSingleTravelProduct))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(RESERVATION_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 예약의_파트너_커스텀_Id가_50자_보다_길면_안된다() {
        // given
        String partnerCustomId = "a".repeat(51);

        LocalDateTime startDateTime = LocalDateTime.of(2023, 10, 18, 13, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 10, 19, 12, 0);
        Customer mockCustomer = new Customer();
        SingleTravelProduct mockSingleTravelProduct = new SingleTravelProduct();

        // when & then
        assertThatThrownBy(() -> new Reservation(partnerCustomId, startDateTime, endDateTime, mockCustomer, mockSingleTravelProduct))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(RESERVATION_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 예약의_종료시간이_시작시간보다_빠르면_안된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2023, 10, 18, 13, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 10, 17, 12, 0);

        Customer mockCustomer = new Customer();
        SingleTravelProduct mockSingleTravelProduct = new SingleTravelProduct();

        // when & then
        assertThatThrownBy(() -> new Reservation("RESERVATION_0001", startDateTime, endDateTime, mockCustomer, mockSingleTravelProduct))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(RESERVATION_FRONT_END_TIME_ERROR.getMessage());
    }
}