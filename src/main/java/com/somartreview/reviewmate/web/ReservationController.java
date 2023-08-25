package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.response.reservation.SingleTravelProductReservationResponse;
import com.somartreview.reviewmate.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "여행상품 예약/주문")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;



    @Operation(operationId = "createSingleTravelProductReservation", summary = "예약 생성", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID 혹은 여행상품 ID"),
    })
    @PostMapping("/client/partners/{partnerDomain}/reservation/travel/single")
    public ResponseEntity<Void> createSingleTravelProductReservation(@PathVariable String partnerDomain,
                                                  @Valid @RequestPart CustomerCreateRequest customerCreateRequest,
                                                  @Valid @RequestPart SingleTravelProductCreateRequest singleTravelProductCreateRequest,
                                                  @RequestPart(required = false) MultipartFile singleTravelProductThumbnail) {
        Long reservationId = reservationService.createSingleTravelProductReservation(partnerDomain, customerCreateRequest, singleTravelProductCreateRequest, singleTravelProductThumbnail);

        return ResponseEntity.created(URI.create("/api/v1/console/partners" + partnerDomain + "/reservations/" + reservationId)).build();
    }



    @Operation(operationId = "getReservationResponseByReservationId", summary = "예약 단일 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "reservationId", description = "예약 ID", example = "1")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 예약 ID"),
    })
    @GetMapping("console/partners/{partnerDomain}/reservations/travel/single/{reservationId}")
    public ResponseEntity<SingleTravelProductReservationResponse> getReservationResponseByReservationId(@PathVariable String partnerDomain,
                                                                                                        @PathVariable Long reservationId) {
        SingleTravelProductReservationResponse reservationResponse = reservationService.getSingleTravelProductReservationResponseById(reservationId);

        return ResponseEntity.ok(reservationResponse);
    }



    @Operation(operationId = "getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct", summary = "예약 목록 조회", description = "⚠️ 아직 정렬이 작동하지 않고, 모든 예약을 불러오기만 함")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "customerPartnerCustomId", description = "고객의 파트너사 커스텀 ID", example = "CUST-1234"),
            @Parameter(name = "travelProductPartnerCustomId", description = "여행상품의 파트너사 커스텀 ID", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID 혹은 여행상품 ID"),
    })
    @GetMapping("console/partners/{partnerDomain}/reservations/travel/single")
    public ResponseEntity<List<SingleTravelProductReservationResponse>> getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(@PathVariable String partnerDomain,
                                                                                                                                                 @RequestParam(name = "customerPartnerCustomId", required = false) String customerPartnerCustomId,
                                                                                                                                                 @RequestParam(name = "travelProductPartnerCustomId", required = false) String travelProductPartnerCustomId) {
        List<SingleTravelProductReservationResponse> reservationResponses = reservationService.getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(partnerDomain, customerPartnerCustomId, travelProductPartnerCustomId);

        return ResponseEntity.ok(reservationResponses);
    }



    @Operation(operationId = "deleteReservation", summary = "예약 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "reservationId", description = "예약 ID", example = "1")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 예약 ID"),
    })
    @DeleteMapping("console/partners/{partnerDomain}/reservations/travel/single/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String partnerDomain,
                                                  @PathVariable Long reservationId) {
        reservationService.deleteByReservationId(reservationId);

        return ResponseEntity.noContent().build();
    }
}
