package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.dto.response.customer.CustomerResponse;
import com.somartreview.reviewmate.service.CustomerService;
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

@Tag(name = "고객/여행객")
@RestController
@RequestMapping("/api/console/v1")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @Operation(operationId = "customerCreateRequest", summary = "고객 생성", description = "⚠️ 개발 환경 및 테스트 용도로만 사용하고, 프로덕션 코드에서는 예약 API를 통해 고객을 생성하세요.")
    @Parameter(name = "partnerDomain", description = "고객이 소속될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "고객 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 고객의 URI, /api/console/v1/customers/{customerId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/{partnerDomain}/customers")
    public ResponseEntity<Void> createCustomer(@PathVariable String partnerDomain,
                                               @Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        final Long customerId = customerService.create(partnerDomain, customerCreateRequest);

        return ResponseEntity.created(URI.create("/api/console/v1/customers/" + customerId)).build();
    }



    @Operation(operationId = "getCustomerResponseByPartnerCustomId", summary = "고객 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "고객이 소속된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 고객 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @GetMapping("/{partnerDomain}/customers/{partnerCustomId}")
    public ResponseEntity<CustomerResponse> getCustomerResponseByPartnerCustomId(@PathVariable String partnerDomain,
                                                                                 @PathVariable String partnerCustomId) {
        CustomerResponse customerResponse = customerService.getCustomerResponseByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);

        return ResponseEntity.ok(customerResponse);
    }



    @Operation(operationId = "getCustomerResponseByCustomerId", summary = "고객 조회")
    @Parameter(name = "customerId", description = "고객 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerResponseByCustomerId(@PathVariable Long customerId) {
        CustomerResponse customerResponse = customerService.getCustomerResponseByCustomerId(customerId);

        return ResponseEntity.ok(customerResponse);
    }



    @Operation(operationId = "updateCustomerByPartnerCustomId", summary = "고객 정보 수정")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "고객이 소속된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 고객 커스텀 ID", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @PutMapping("/{partnerDomain}/customers/{partnerCustomId}")
    public ResponseEntity<Void> updateCustomerByPartnerCustomId(@PathVariable String partnerDomain,
                                                           @PathVariable String partnerCustomId,
                                                           @Valid @RequestBody CustomerUpdateRequest request) {
        customerService.updateByPartnerCustomId(partnerDomain, partnerCustomId, request);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "updateCustomerByCustomerId", summary = "고객 정보 수정")
    @Parameter(name = "customerId", description = "고객 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Void> updateCustomerByCustomerId(@PathVariable Long customerId,
                                                           @Valid @RequestBody CustomerUpdateRequest request) {
        customerService.updateByCustomerId(customerId, request);

        return ResponseEntity.noContent().build();
    }



    @Operation(operationId = "deleteCustomerByPartnerCustomId", summary = "고객 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "고객이 소속된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 고객 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @DeleteMapping("/{partnerDomain}/customers/{partnerCustomId}")
    public ResponseEntity<Void> deleteCustomerByPartnerCustomId(@PathVariable String partnerDomain,
                                                           @PathVariable String partnerCustomId) {
        customerService.deleteByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);

        return ResponseEntity.noContent().build();
    }



    @Operation(operationId = "deleteCustomerByCustomerId", summary = "고객 삭제")
    @Parameter(name = "customerId", description = "고객 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomerByCustomerId(@PathVariable Long customerId) {
        customerService.deleteByCustomerId(customerId);

        return ResponseEntity.noContent().build();
    }
}
