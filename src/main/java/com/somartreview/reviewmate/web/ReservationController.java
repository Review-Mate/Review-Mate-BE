package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.dto.product.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.reservation.SingleTravelReservationCreateRequest;
import com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse;
import com.somartreview.reviewmate.service.CustomerService;
import com.somartreview.reviewmate.service.ReservationDeleteService;
import com.somartreview.reviewmate.service.ReservationService;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import com.somartreview.reviewmate.service.partners.PartnerSellerService;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Tag(name = "여행상품 예약/주문")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationDeleteService reservationDeleteService;


    @Operation(operationId = "createSingleTravelProductReservation", summary = "예약 생성", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "partnerDomain", description = "주문이 등록될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "생성 성공", headers = {
            @Header(name = "Location", description = "생성된 예약의 URI, /api/console/v1/products/travel/single/reservations/{reservationId}", schema = @Schema(type = "string"))
    })
    @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID 혹은 여행상품 ID")
    @PostMapping(value = "/client/v1/{partnerDomain}/products/travel/single/reservations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createSingleTravelProductReservation(@PathVariable String partnerDomain,
                                                                     @Valid @RequestPart SingleTravelReservationCreateRequest singleTravelReservationCreateRequest,
                                                                     @RequestPart(required = false) MultipartFile singleTravelProductThumbnail) {
        Long reservationId = reservationService.createSingleTravelProductReservation(partnerDomain, singleTravelReservationCreateRequest, singleTravelProductThumbnail);

        return ResponseEntity.created(URI.create("/api/console/v1/products/travel/single/reservations/" + reservationId)).build();
    }


    @Operation(operationId = "getReservationResponseByReservationId", summary = "예약 단일 조회")
    @Parameter(name = "reservationId", description = "예약 ID", example = "1")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 예약 ID")
    @GetMapping("/console/v1/products/travel/single/reservations/{reservationId}")
    public ResponseEntity<SingleTravelProductReservationResponse> getReservationResponseByReservationId(@PathVariable Long reservationId) {
        SingleTravelProductReservationResponse reservationResponse = reservationService.getSingleTravelProductReservationResponseById(reservationId);

        return ResponseEntity.ok(reservationResponse);
    }


    @Operation(operationId = "getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct", summary = "예약 목록 조회")
    @Parameter(name = "customerId", description = "고객 ID")
    @Parameter(name = "singleTravelProductId", description = "여행상품 ID")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID 혹은 여행상품 ID")
    @GetMapping("/console/v1/products/travel/single/reservations")
    public ResponseEntity<List<SingleTravelProductReservationResponse>> getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(@RequestParam(name = "customerId", required = false) Long customerId,
                                                                                                                                                 @RequestParam(name = "singleTravelProductId", required = false) Long singleTravelProductId) {
        List<SingleTravelProductReservationResponse> reservationResponses = reservationService.getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(customerId, singleTravelProductId);

        return ResponseEntity.ok(reservationResponses);
    }


    @Operation(operationId = "deleteReservation", summary = "예약 삭제")
    @Parameter(name = "reservationId", description = "예약 ID")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 예약 ID")
    @DeleteMapping("console/products/travel/single/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationDeleteService.delete(reservationId);

        return ResponseEntity.noContent().build();
    }
}
