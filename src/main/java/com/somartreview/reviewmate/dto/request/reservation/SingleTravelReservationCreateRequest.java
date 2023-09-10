package com.somartreview.reviewmate.dto.request.reservation;

import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.travelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelReservationCreateRequest {

    @NotBlank
    @Schema(description = "파트너사가 부여한 예약 ID", example = "RESERVATION_0001")
    private String partnerCustomId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "여행상품 이용시작 날짜시간 { pattern: 'yyyy-MM-dd'T'HH:mm:ss' }", example = "2023-10-18T13:00:00")
    private LocalDateTime startDateTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "여행상품 이용종료 날짜시간 { pattern: 'yyyy-MM-dd'T'HH:mm:ss' }", example = "2023-10-19T12:00:00")
    private LocalDateTime endDateTime;

    private CustomerCreateRequest customerCreateRequest;

    private SingleTravelProductCreateRequest singleTravelProductCreateRequest;


    public Reservation toEntity(final Customer customer, final SingleTravelProduct singleTravelProduct) {
        return Reservation.builder()
                .partnerCustomId(partnerCustomId)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .customer(customer)
                .travelProduct(singleTravelProduct)
                .build();
    }
}
