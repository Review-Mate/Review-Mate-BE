package com.somartreview.reviewmate.web.partners;

import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerSeller.PartnerSellerResponse;
import com.somartreview.reviewmate.service.partners.PartnerSellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "판매자")
@RestController
@RequestMapping("/api/console/v1/sellers")
@RequiredArgsConstructor
public class PartnerSellerController {

    private final PartnerSellerService partnerSellerService;


    @Operation(operationId = "createPartnerSeller", summary = "판매자 생성")
    @ApiResponse(responseCode = "201", description = "판매자 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 리뷰의 URI, /api/console/v1/sellers/{partnerSellerId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> createPartnerSeller(@Valid @RequestBody PartnerSellerCreateRequest partnerSellerCreateRequest) {
        Long partnerSellerId = partnerSellerService.create(partnerSellerCreateRequest);

        return ResponseEntity.created(URI.create("/api/console/v1/sellers/" + partnerSellerId)).build();
    }


    
    @Operation(operationId = "getPartnerSellerResponseById", summary = "판매자 조회")
    @Parameter(name = "partnerSellerId", description = "조회 할 판매자 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매자 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @GetMapping("/{partnerSellerId}")
    public ResponseEntity<PartnerSellerResponse> getPartnerSellerResponseById(@PathVariable Long partnerSellerId) {
        PartnerSellerResponse partnerSellerResponse = partnerSellerService.getPartnerSellerResponseById(partnerSellerId);

        return ResponseEntity.ok(partnerSellerResponse);
    }


    
    @Operation(operationId = "updatePartnerSellerById", summary = "판매자 정보 수정")
    @Parameter(name = "partnerSellerId", description = "수정 할 판매자 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "판매자 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 전화번호"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @PutMapping("/{partnerSellerId}")
    public ResponseEntity<Void> updatePartnerSellerById(@PathVariable Long partnerSellerId,
                                                        @Valid @RequestBody PartnerSellerUpdateRequest partnerSellerUpdateRequest) {
        partnerSellerService.updateById(partnerSellerId, partnerSellerUpdateRequest);

        return ResponseEntity.noContent().build();
    }


    
    @Operation(operationId = "deletePartnerSellerById", summary = "판매자 삭제")
    @Parameter(name = "partnerSellerId", description = "삭제 할 판매자 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "판매자 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @DeleteMapping("/{partnerSellerId}")
    public ResponseEntity<Void> deletePartnerSellerById(@PathVariable Long partnerSellerId) {
        partnerSellerService.deleteById(partnerSellerId);

        return ResponseEntity.noContent().build();
    }
}
