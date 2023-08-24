package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.dto.response.customer.CustomerResponse;
import com.somartreview.reviewmate.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "고객/여행객", description = "⚠️ 개발 환경 및 테스트 용도로만 사용하고, 프로덕션 코드에서는 예약 API를 통해 고객을 생성하세요.")
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @Operation(operationId = "customerCreateRequest", summary = "고객 생성")
    @ApiResponse(responseCode = "201", description = "고객 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 고객의 URI, /api/v1/customers/{customerId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        Long customerId = customerService.createCustomer(customerCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/customers/" + customerId)).build();
    }


    @Operation(operationId = "getCustomerResponseById", summary = "고객 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerResponseById(@PathVariable Long id) {
        CustomerResponse customerResponse = customerService.getCustomerResponseById(id);

        return ResponseEntity.ok(customerResponse);
    }


    @Operation(operationId = "updateCustomerByCustomerId", summary = "고객 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateCustomerByCustomerId(@PathVariable Long customerId, @RequestBody CustomerUpdateRequest request) {
        customerService.updateCustomerById(customerId, request);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deleteCustomerByCustomerId", summary = "고객 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "고객 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 고객 ID")
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerByCustomerId(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);

        return ResponseEntity.noContent().build();
    }
}
