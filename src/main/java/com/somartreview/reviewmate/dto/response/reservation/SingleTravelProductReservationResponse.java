package com.somartreview.reviewmate.dto.response.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductReservationResponse {

    @Schema(description = "예약 ID")
    private Long id;

    @Schema(description = "파트너사가 부여한 예약 ID")
    private String reservationPartnerCustomId;

    @Schema(description = "고객의 파트너사 커스텀 ID")
    private String customerPartnerCustomId;

    @Schema(description = "고객 이름", example = "권순찬")
    private String customerName;

    @Schema(description = "고객 전화번호", example = "010-1234-5678")
    private String customerPhoneNumber;

    @Schema(description = "여행상품의 파트너사 커스텀 ID")
    private String travelProductPartnerCustomId;

    @Schema(description = "여행상품 이름", example = "신라더스테이 호텔")
    private String travelProductName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 시작 시간 { pattern: 'yyyy.MM.dd'T'HH:mm' ")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 종료 시간 { pattern: 'yyyy.MM.dd'T'HH:mm' ")
    private LocalDateTime endTime;

    public SingleTravelProductReservationResponse(final Reservation reservation) {
        this.id = reservation.getId();
        this.reservationPartnerCustomId = reservation.getPartnerCustomId();
        this.customerPartnerCustomId = reservation.getCustomer().getPartnerCustomId();
        this.customerName = reservation.getCustomer().getName();
        this.customerPhoneNumber = reservation.getCustomer().getPhoneNumber();

        final SingleTravelProduct singleTravelProduct = (SingleTravelProduct) reservation.getTravelProduct();
        this.travelProductPartnerCustomId = singleTravelProduct.getPartnerCustomId();
        this.travelProductName = singleTravelProduct.getName();
        this.startTime = reservation.getStartDate().atTime(singleTravelProduct.getStartTime());
        this.endTime = reservation.getStartDate().atTime(singleTravelProduct.getEndTime());
    }
}
