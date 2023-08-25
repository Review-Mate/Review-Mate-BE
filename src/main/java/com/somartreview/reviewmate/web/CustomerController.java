package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.domain.Customer.CustomerId;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerIdDto;
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

@Tag(name = "고객/여행객", description = "⚠️ 개발 환경 및 테스트 용도로만 사용하고, 프로덕션 코드에서는 예약 API를 통해 고객을 생성하세요.")
@RestController
@RequestMapping("/api/v1/dev/partners/{partnerDomain}/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @Operation(operationId = "customerCreateRequest", summary = "고객 생성")
    @Parameter(name = "partnerDomain", description = "고객이 소속될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "고객 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 고객의 URI, /api/v1/partners/{partnerDomain}/customers/{partnerCustomId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> createCustomer(@PathVariable String partnerDomain,
                                               @Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        final CustomerId customerId = customerService.create(partnerDomain, customerCreateRequest).getCustomerId();

        return ResponseEntity.created(URI.create("/api/v1/dev/partners/" + customerId.getPartnerDomain() + "customers/" + customerId.getPartnerCustomId())).build();
    }


    @Operation(operationId = "getCustomerResponseById", summary = "고객 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "고객이 소속된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 고객 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @GetMapping("/{partnerCustomId}")
    public ResponseEntity<CustomerResponse> getCustomerResponseById(@PathVariable String partnerDomain,
                                                                    @PathVariable String partnerCustomId) {
        CustomerIdDto customerIdDto = new CustomerIdDto(partnerDomain, partnerCustomId);
        CustomerResponse customerResponse = customerService.getCustomerResponseByCustomId(customerIdDto);

        return ResponseEntity.ok(customerResponse);
    }


    @Operation(operationId = "updateCustomerByCustomerId", summary = "고객 정보 수정")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "고객이 소속된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 고객 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @PutMapping("/{partnerCustomId}")
    public ResponseEntity<Void> updateCustomerByCustomerId(@PathVariable String partnerDomain,
                                                           @PathVariable String partnerCustomId,
                                                           @Valid @RequestBody CustomerUpdateRequest request) {
        CustomerIdDto customerIdDto = new CustomerIdDto(partnerDomain, partnerCustomId);
        customerService.updateByCustomerId(customerIdDto, request);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deleteCustomerByCustomerId", summary = "고객 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "고객이 소속된 파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerCustomId", description = "파트너사가 정의하는 고객 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @DeleteMapping("/{partnerCustomId}")
    public ResponseEntity<Void> deleteCustomerByCustomerId(@PathVariable String partnerDomain,
                                                           @PathVariable String partnerCustomId) {
        CustomerIdDto customerIdDto = new CustomerIdDto(partnerDomain, partnerCustomId);
        customerService.deleteByCustomerId(customerIdDto);

        return ResponseEntity.noContent().build();
    }
}
