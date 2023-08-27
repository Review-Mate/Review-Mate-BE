package com.somartreview.reviewmate.web.products;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProductCategory;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProductId;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.TravelProductIdDto;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SingleTravelProductController {

    private final SingleTravelProductService singleTravelProductService;


    @Operation(operationId = "createSingleTravelProduct", summary = "단일 여행상품 생성", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "partnerDomain", description = "단일 여행상품이 등록될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "단일 여행상품 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 단일 여행상품의 URI, /api/v1/partners/{partnerDomain}/products/travel/single/{partnerCustomId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/dev/partners/{partnerDomain}/products/travel/single")
    public ResponseEntity<Void> createSingleTravelProduct(@PathVariable String partnerDomain,
                                                          @Valid @RequestPart SingleTravelProductCreateRequest singleTravelProductCreateRequest,
                                                          @RequestPart(required = false) MultipartFile thumbnailFile) {
        final TravelProductId travelProductId = singleTravelProductService.create(partnerDomain, singleTravelProductCreateRequest, thumbnailFile).getTravelProductId();

        return ResponseEntity.created(URI.create("/api/v1/dev/partners/" + travelProductId.getPartnerDomain() + "/products/travel/single/" + travelProductId.getPartnerCustomId())).build();
    }


    @Operation(operationId = "getSingleTravelProductResponseByTravelProductId", summary = "단일 여행상품 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "상품이 등록된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 상품 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "단일 여행상품 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @GetMapping("/dev/partners/{partnerDomain}/products/travel/single/{partnerCustomId}")
    public ResponseEntity<SingleTravelProductResponse> getSingleTravelProductResponseByTravelProductId(@PathVariable String partnerDomain,
                                                                                                       @PathVariable String partnerCustomId) {
        TravelProductIdDto travelProductIdDto = new TravelProductIdDto(partnerDomain, partnerCustomId);
        SingleTravelProductResponse singleTravelProductResponse = singleTravelProductService.getSingleTravelProductResponseByTravelProductId(travelProductIdDto);

        return ResponseEntity.ok(singleTravelProductResponse);
    }


    @Operation(operationId = "getSingleTravelProductConsoleElementResponseByTravelProductId", summary = "단일 여행상품 조회")
    @GetMapping("/console/partners/{partnerDomain}/products/travel/single/{partnerCustomId}")
    public ResponseEntity<SingleTravelProductConsoleElementResponse> getSingleTravelProductConsoleElementResponseByTravelProductId(@PathVariable String partnerDomain,
                                                                                                                                   @PathVariable String partnerCustomId) {
        TravelProductIdDto travelProductIdDto = new TravelProductIdDto(partnerDomain, partnerCustomId);
        SingleTravelProductConsoleElementResponse singleTravelProductConsoleElementResponse = singleTravelProductService.getSingleTravelProductConsoleElementResponseByTravelProductId(travelProductIdDto);

        return ResponseEntity.ok(singleTravelProductConsoleElementResponse);
    }


    @Operation(operationId = "getSingleTravelProductResponsesByCategory", summary = "카테고리 별 단일 여행상품들 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "상품이 등록된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "category", description = "단일 여행상품 카테고리", example = "ACCOMMODATION")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @GetMapping("/dev/partners/{partnerDomain}/products/travel/single")
    public ResponseEntity<List<SingleTravelProductResponse>> getSingleTravelProductResponsesByCategory(@PathVariable String partnerDomain,
                                                                                                       @RequestParam(name = "category") TravelProductCategory travelProductCategory) {
        List<SingleTravelProductResponse> singleTravelProductResponses = singleTravelProductService.getSingleTravelProductResponsesByPartnerDomainAndCategory(partnerDomain, travelProductCategory);

        return ResponseEntity.ok(singleTravelProductResponses);
    }


    @Operation(operationId = "getSingleTravelProductConsoleElementResponsesByCategory", summary = "카테고리 별 단일 여행상품들 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "상품이 등록된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "category", description = "단일 여행상품 카테고리", example = "ACCOMMODATION")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @GetMapping("/console/partners/{partnerDomain}/products/travel/single")
    public ResponseEntity<List<SingleTravelProductConsoleElementResponse>> getSingleTravelProductConsoleElementResponsesByCategory(@PathVariable String partnerDomain,
                                                                                                                                   @RequestParam(name = "category") TravelProductCategory travelProductCategory) {
        List<SingleTravelProductConsoleElementResponse> singleTravelProductConsoleElementResponses = singleTravelProductService.getSingleTravelProductConsoleElementResponsesByPartnerDomainAndCategory(partnerDomain, travelProductCategory);

        return ResponseEntity.ok(singleTravelProductConsoleElementResponses);
    }



    @Operation(operationId = "updateSingleTravelProductByTravelProductId", summary = "단일 여행상품 정보 수정", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "partnerDomain", description = "단일 여행상품이 등록될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "단일 여행상품 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @PutMapping("/dev/partners/{partnerDomain}/products/travel/single/{partnerCustomId}")
    public ResponseEntity<Void> updateSingleTravelProductByTravelProductId(@PathVariable String partnerDomain,
                                                                            @PathVariable String partnerCustomId,
                                                                            @Valid @RequestBody SingleTravelProductUpdateRequest singleTravelProductUpdateRequest,
                                                                            @RequestPart(required = false) MultipartFile thumbnailFile) {
        TravelProductIdDto travelProductIdDto = new TravelProductIdDto(partnerDomain, partnerCustomId);
        singleTravelProductService.updateByTravelProductId(travelProductIdDto, singleTravelProductUpdateRequest, thumbnailFile);

        return ResponseEntity.noContent().build();
    }



    @Operation(operationId = "deleteSingleTravelProductByTravelProductId", summary = "단일 여행상품 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "단일 여행상품이 등록된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 단일 여행상품 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "단일 여행상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 단일 여행상품 ID")
    })
    @DeleteMapping("/dev/partners/{partnerDomain}/products/travel/single/{partnerCustomId}")
    public ResponseEntity<Void> deleteSingleTravelProductByTravelProductId(@PathVariable String partnerDomain,
                                                                            @PathVariable String partnerCustomId) {
        TravelProductIdDto travelProductIdDto = new TravelProductIdDto(partnerDomain, partnerCustomId);
        singleTravelProductService.deleteByTravelProductId(travelProductIdDto);

        return ResponseEntity.noContent().build();
    }
}
