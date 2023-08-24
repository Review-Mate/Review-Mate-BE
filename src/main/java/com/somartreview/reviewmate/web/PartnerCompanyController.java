package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.dto.request.partnerCompany.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.response.partnerCompany.PartnerCompanyResponse;
import com.somartreview.reviewmate.service.PartnerCompanyService;
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

import java.net.URI;

@Tag(name = "파트너사", description = "⚠️ 개발 환경 및 테스트 용도로만 id로 호출하고, 프로덕션 코드에서는 도메인을 기준으로 호출해주세요.")
@RestController
@RequiredArgsConstructor
public class PartnerCompanyController {

    private final PartnerCompanyService partnerCompanyService;


    @Operation(operationId = "createPartnerCompany", summary = "파트너사 생성")
    @ApiResponse(responseCode = "201", description = "파트너사 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 파트너사의 URI, /api/v1/partners/companies/{partnerCompanyId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/api/v1/partners/companies")
    public ResponseEntity<Void> createPartnerCompany(@RequestBody PartnerCompanyCreateRequest partnerCompanyCreateRequest) {
        String partnerDomain = partnerCompanyService.createPartnerCompany(partnerCompanyCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/" + partnerDomain + "/companies")).build();
    }


    @Operation(operationId = "getPartnerCompanyResponseByDomain", summary = "파트너사 조회")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너사 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    })
    @GetMapping("/api/v1/{partnerDomain}/companies")
    public ResponseEntity<PartnerCompanyResponse> getPartnerCompanyResponseByDomain(@PathVariable String partnerDomain) {
        PartnerCompanyResponse partnerCompanyResponse = partnerCompanyService.getPartnerCompanyResponseByDomain(partnerDomain);

        return ResponseEntity.ok(partnerCompanyResponse);
    }


    @Deprecated
    @Operation(operationId = "getPartnerCompanyResponseById", summary = "파트너사 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너사 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 ID")
    })
    @GetMapping("/api/v1/partners/{partnerCompanyId}/companies")
    public ResponseEntity<PartnerCompanyResponse> getPartnerCompanyResponseByPartnerCompanyId(@PathVariable Long partnerCompanyId) {
        PartnerCompanyResponse partnerCompanyResponse = partnerCompanyService.getPartnerCompanyResponseById(partnerCompanyId);

        return ResponseEntity.ok(partnerCompanyResponse);
    }


    @Operation(operationId = "updatePartnerCompanyByPartnerDomain", summary = "파트너사 정보 수정")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    })
    @PutMapping("/api/v1/{partnerDomain}/companies")
    public ResponseEntity<Void> updatePartnerCompanyByPartnerDomain(@PathVariable String partnerDomain,
                                                                    @RequestBody PartnerCompanyUpdateRequest request) {
        partnerCompanyService.updatePartnerCompanyByDomain(partnerDomain, request);

        return ResponseEntity.noContent().build();
    }


    @Deprecated
    @Operation(operationId = "updatePartnerCompanyByPartnerCompanyId", summary = "파트너사 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 ID")
    })
    @PutMapping("/api/v1/partners/{partnerCompanyId}/companies")
    public ResponseEntity<Void> updatePartnerCompanyByPartnerCompanyId(@PathVariable Long partnerCompanyId,
                                                                       @RequestBody PartnerCompanyUpdateRequest request) {
        partnerCompanyService.updatePartnerCompanyById(partnerCompanyId, request);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deletePartnerCompanyByPartnerCompanyId", summary = "파트너사 삭제")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 ID")
    })
    @DeleteMapping("/api/v1/{partnerDomain}/companies")
    public ResponseEntity<Void> deletePartnerCompanyByPartnerDomain(@PathVariable String partnerDomain) {
        partnerCompanyService.deletePartnerCompanyByDomain(partnerDomain);

        return ResponseEntity.noContent().build();
    }


    @Deprecated
    @Operation(operationId = "deletePartnerCompanyByPartnerDomain", summary = "파트너사 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    })
    @DeleteMapping("/api/v1/partners/{partnerCompanyId}/companies")
    public ResponseEntity<Void> deletePartnerCompanyById(@PathVariable Long partnerCompanyId) {
        partnerCompanyService.deletePartnerCompanyById(partnerCompanyId);

        return ResponseEntity.noContent().build();
    }
}
