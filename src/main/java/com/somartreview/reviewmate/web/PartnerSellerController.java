package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerSeller.PartnerSellerResponse;
import com.somartreview.reviewmate.service.PartnerSellerService;
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

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "판매자", description = "⚠️ 개발 환경 및 테스트 용도로만 id로 호출하고, 프로덕션 코드에서는 phoneNumber로 기준으로 호출해주세요.")
@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
public class PartnerSellerController {

    private final PartnerSellerService partnerSellerService;


    @Operation(operationId = "createPartnerSeller", summary = "판매자 생성")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "판매자 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 판매자의 URI, /api/v1/{partnerDomain}/sellers/{partnerSellerId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/{partnerDomain}/sellers")
    public ResponseEntity<Void> createPartnerSeller(@PathVariable String partnerDomain,
                                                    @Valid @RequestBody PartnerSellerCreateRequest partnerSellerCreateRequest) {
        Long partnerSellerId = partnerSellerService.createPartnerSeller(partnerDomain, partnerSellerCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/" + partnerDomain + "/sellers/" + partnerSellerId)).build();
    }


    @Deprecated
    @Operation(operationId = "getPartnerSellerResponseById", summary = "판매자 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerSellerId", description = "조회 할 판매자 ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매자 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @GetMapping("/{partnerDomain}/sellers/{partnerSellerId}")
    public ResponseEntity<PartnerSellerResponse> getPartnerSellerResponseById(@PathVariable String partnerDomain,
                                                                               @PathVariable Long partnerSellerId) {
        PartnerSellerResponse partnerSellerResponse = partnerSellerService.getPartnerSellerResponseById(partnerDomain, partnerSellerId);

        return ResponseEntity.ok(partnerSellerResponse);
    }


    @Operation(operationId = "getPartnerSellerResponseByPhoneNumber", summary = "판매자 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "phoneNumber", description = "조회 할 판매자 전화번호")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "판매자 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 전화번호"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @GetMapping("/{partnerDomain}/sellers/{phoneNumber}")
    public ResponseEntity<PartnerSellerResponse> getPartnerSellerResponseByPhoneNumber(@PathVariable String partnerDomain,
                                                                                        @PathVariable String phoneNumber) {
        PartnerSellerResponse partnerSellerResponse = partnerSellerService.getPartnerSellerResponseByPhoneNumber(partnerDomain, phoneNumber);

        return ResponseEntity.ok(partnerSellerResponse);
    }


    @Deprecated
    @Operation(operationId = "updatePartnerSellerById", summary = "판매자 정보 수정")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerSellerId", description = "수정 할 판매자 ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "판매자 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 전화번호"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @PutMapping("/{partnerDomain}/sellers/{partnerSellerId}")
    public ResponseEntity<Void> updatePartnerSellerById(@PathVariable String partnerDomain,
                                                        @PathVariable Long partnerSellerId,
                                                        @Valid @RequestBody PartnerSellerUpdateRequest partnerSellerUpdateRequest) {
        partnerSellerService.updatePartnerSellerById(partnerDomain, partnerSellerId, partnerSellerUpdateRequest);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "updatePartnerSellerByPhoneNumber", summary = "판매자 정보 수정")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "phoneNumber", description = "수정 할 판매자 전화번호")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "판매자 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 전화번호"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @PutMapping("/{partnerDomain}/sellers/{phoneNumber}")
    public ResponseEntity<Void> updatePartnerSellerByPhoneNumber(@PathVariable String partnerDomain,
                                                                 @PathVariable String phoneNumber,
                                                                 @Valid @RequestBody PartnerSellerUpdateRequest partnerSellerUpdateRequest) {
        partnerSellerService.updatePartnerSellerByPhoneNumber(partnerDomain, phoneNumber, partnerSellerUpdateRequest);

        return ResponseEntity.noContent().build();
    }


    @Deprecated
    @Operation(operationId = "deletePartnerSellerById", summary = "판매자 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerSellerId", description = "삭제 할 판매자 ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "판매자 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @DeleteMapping("/{partnerDomain}/sellers/{partnerSellerId}")
    public ResponseEntity<Void> deletePartnerSellerById(@PathVariable String partnerDomain,
                                                        @PathVariable Long partnerSellerId) {
        partnerSellerService.deletePartnerSellerById(partnerDomain, partnerSellerId);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deletePartnerSellerByPhoneNumber", summary = "판매자 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "phoneNumber", description = "삭제 할 판매자 전화번호")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "판매자 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 판매자 전화번호"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 판매자가 소속된 도메인이 다름")
    })
    @DeleteMapping("/{partnerDomain}/sellers/{phoneNumber}")
    public ResponseEntity<Void> deletePartnerSellerByPhoneNumber(@PathVariable String partnerDomain,
                                                                 @PathVariable String phoneNumber) {
        partnerSellerService.deletePartnerSellerByPhoneNumber(partnerDomain, phoneNumber);

        return ResponseEntity.noContent().build();
    }
}
