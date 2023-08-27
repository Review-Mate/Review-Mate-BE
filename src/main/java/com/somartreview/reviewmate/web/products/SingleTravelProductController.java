package com.somartreview.reviewmate.web.products;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProductCategory;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.response.travelProduct.SingleTravelProductConsoleElementResponse;
import com.somartreview.reviewmate.dto.response.travelProduct.SingleTravelProductResponse;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Tag(name = "단일 여행상품")
@RestController
@RequestMapping("/api/console/v1")
@RequiredArgsConstructor
public class SingleTravelProductController {

    private final SingleTravelProductService singleTravelProductService;


    @Operation(operationId = "createSingleTravelProduct", summary = "단일 여행상품 생성", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "partnerDomain", description = "단일 여행상품이 등록될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "단일 여행상품 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 단일 여행상품의 URI, /api/console/v1/products/travel/single/{singleTravelProductId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/{partnerDomain}/products/travel/single")
    public ResponseEntity<Void> createSingleTravelProduct(@PathVariable String partnerDomain,
                                                          @Valid @RequestPart SingleTravelProductCreateRequest singleTravelProductCreateRequest,
                                                          @RequestPart(required = false) MultipartFile thumbnailFile) {
        final Long singleTravelProductId = singleTravelProductService.create(partnerDomain, singleTravelProductCreateRequest, thumbnailFile);

        return ResponseEntity.created(URI.create("/api/console/v1/products/travel/single/" + singleTravelProductId)).build();
    }



    @Operation(operationId = "getSingleTravelProductConsoleElementResponseByTravelProductId", summary = "단일 여행상품 단독 조회")
    @Parameter(name = "travelProductId", description = "단일 여행상품 ID")
    @GetMapping("/products/travel/single/{travelProductId}")
    public ResponseEntity<SingleTravelProductConsoleElementResponse> getSingleTravelProductConsoleElementResponseByTravelProductId(@PathVariable Long travelProductId) {
        SingleTravelProductConsoleElementResponse singleTravelProductConsoleElementResponse = singleTravelProductService.getSingleTravelProductConsoleElementResponseByTravelProductId(travelProductId);

        return ResponseEntity.ok(singleTravelProductConsoleElementResponse);
    }



    @Operation(operationId = "getSingleTravelProductConsoleElementResponseByPartnerCustomId", summary = "단일 여행상품 단독 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "단일 여행상품이 등록된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 단일 여행상품 커스텀 ID (unique)", example = "PRODUCT-0001")
    })
    @GetMapping("/{partnerDomain}/products/travel/single/{partnerCustomId}")
    public ResponseEntity<SingleTravelProductConsoleElementResponse> getSingleTravelProductConsoleElementResponseByPartnerCustomId(@PathVariable String partnerDomain,
                                                                                                                                   @PathVariable String partnerCustomId) {
        SingleTravelProductConsoleElementResponse singleTravelProductConsoleElementResponse = singleTravelProductService.getSingleTravelProductConsoleElementResponseByPartnerCustomId(partnerDomain, partnerCustomId);

        return ResponseEntity.ok(singleTravelProductConsoleElementResponse);
    }



    @Operation(operationId = "getSingleTravelProductConsoleElementResponsesByCategory", summary = "카테고리 별 단일 여행상품 목록 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "상품이 등록된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "category", description = "단일 여행상품 카테고리", example = "ACCOMMODATION")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @GetMapping("/console/{partnerDomain}/products/travel/single")
    public ResponseEntity<List<SingleTravelProductConsoleElementResponse>> getSingleTravelProductConsoleElementResponsesByCategory(@PathVariable String partnerDomain,
                                                                                                                                   @RequestParam(name = "category") TravelProductCategory travelProductCategory) {
        List<SingleTravelProductConsoleElementResponse> singleTravelProductConsoleElementResponses = singleTravelProductService.getSingleTravelProductConsoleElementResponsesByPartnerDomainAndTravelProductCategory(partnerDomain, travelProductCategory);

        return ResponseEntity.ok(singleTravelProductConsoleElementResponses);
    }


    @Operation(operationId = "updateSingleTravelProductByTravelProductId", summary = "단일 여행상품 정보 수정", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "travelProductId", description = "단일 여행상품 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "단일 여행상품 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @PutMapping("/products/travel/single/{travelProductId}")
    public ResponseEntity<Void> updateSingleTravelProductByTravelProductId(@PathVariable Long travelProductId,
                                                                           @Valid @RequestBody SingleTravelProductUpdateRequest singleTravelProductUpdateRequest,
                                                                           @RequestPart(required = false) MultipartFile thumbnailFile) {
        singleTravelProductService.updateByTravelProductId(travelProductId, singleTravelProductUpdateRequest, thumbnailFile);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deleteSingleTravelProductByTravelProductId", summary = "단일 여행상품 삭제")
    @Parameter(name = "travelProductId", description = "단일 여행상품 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "단일 여행상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @DeleteMapping("/products/travel/single/{travelProductId}")
    public ResponseEntity<Void> deleteSingleTravelProductByTravelProductId(@PathVariable Long travelProductId) {
        singleTravelProductService.deleteByTravelProductId(travelProductId);

        return ResponseEntity.noContent().build();
    }
}
