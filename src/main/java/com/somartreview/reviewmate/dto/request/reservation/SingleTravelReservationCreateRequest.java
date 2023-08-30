package com.somartreview.reviewmate.dto.request.reservation;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelReservationCreateRequest {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "여행상품 이용날 { pattern: 'yyyy-MM-dd' }", example = "2021-08-01")
    private LocalDate startDate;

    private CustomerCreateRequest customerCreateRequest;

    private SingleTravelProductCreateRequest singleTravelProductCreateRequest;


    public Reservation toEntity(final Customer customer, final SingleTravelProduct singleTravelProduct) {
        return Reservation.builder()
                .startDate(startDate)
                .customer(customer)
                .travelProduct(singleTravelProduct)
                .build();
    }
}
