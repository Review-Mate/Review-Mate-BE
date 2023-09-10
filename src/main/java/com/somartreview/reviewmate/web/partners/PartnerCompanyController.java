package com.somartreview.reviewmate.web.partners;

import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyResponse;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "파트너사")
@RestController
@RequestMapping("/api/console/v1/companies")
@RequiredArgsConstructor
public class PartnerCompanyController {

    private final PartnerCompanyService partnerCompanyService;


    @Operation(operationId = "createPartnerCompany", summary = "파트너사 생성")
    @ApiResponse(responseCode = "201", description = "파트너사 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 파트너사의 URI, /api/console/v1/companies/{partnerDomain}", schema = @Schema(type = "string"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> createPartnerCompany(@Valid @RequestBody PartnerCompanyCreateRequest partnerCompanyCreateRequest) {
        String partnerDomain = partnerCompanyService.create(partnerCompanyCreateRequest);

        return ResponseEntity.created(URI.create("/api/console/v1/companies/" + partnerDomain)).build();
    }


    @Operation(operationId = "getPartnerCompanyResponseByDomain", summary = "파트너사 조회")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "200", description = "파트너사 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    @GetMapping("/{partnerDomain}")
    public ResponseEntity<PartnerCompanyResponse> getPartnerCompanyResponseByDomain(@PathVariable String partnerDomain) {
        PartnerCompanyResponse partnerCompanyResponse = partnerCompanyService.getPartnerCompanyResponseByDomain(partnerDomain);

        return ResponseEntity.ok(partnerCompanyResponse);
    }


    @Operation(operationId = "updatePartnerCompanyByPartnerDomain", summary = "파트너사 정보 수정")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "204", description = "파트너사 정보 수정 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    @PutMapping("/{partnerDomain}")
    public ResponseEntity<Void> updatePartnerCompanyByPartnerDomain(@PathVariable String partnerDomain,
                                                                    @Valid @RequestBody PartnerCompanyUpdateRequest request) {
        partnerCompanyService.updateByPartnerDomain(partnerDomain, request);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deletePartnerCompanyByPartnerCompanyId", summary = "파트너사 삭제")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "204", description = "파트너사 삭제 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    @DeleteMapping("/{partnerDomain}")
    public ResponseEntity<Void> deletePartnerCompanyByPartnerDomain(@PathVariable String partnerDomain) {
        partnerCompanyService.deleteByPartnerDomain(partnerDomain);

        return ResponseEntity.noContent().build();
    }


}
