package com.somartreview.reviewmate.web.partners;

import com.somartreview.reviewmate.dto.request.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.dto.request.partnerCompany.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.response.partnerCompany.PartnerCompanyResponse;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
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

@Tag(name = "파트너사", description = "⚠️ 개발 환경 및 테스트 용도로만 id로 호출하고, 프로덕션 코드에서는 domain을 기준으로 호출해주세요.")
@RestController
@RequestMapping("/api/v1/console/partners")
@RequiredArgsConstructor
public class PartnerCompanyController {

    private final PartnerCompanyService partnerCompanyService;


    @Operation(operationId = "createPartnerCompany", summary = "파트너사 생성")
    @ApiResponse(responseCode = "201", description = "파트너사 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 파트너사의 URI, /api/v1/partners/{partnerDomain}/companies", schema = @Schema(type = "string"))
    })
    @PostMapping("/companies")
    public ResponseEntity<Void> createPartnerCompany(@Valid @RequestBody PartnerCompanyCreateRequest partnerCompanyCreateRequest) {
        String partnerDomain = partnerCompanyService.create(partnerCompanyCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/console/partners/" + partnerDomain)).build();
    }


    @Operation(operationId = "getPartnerCompanyResponseByDomain", summary = "파트너사 조회")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너사 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    })
    @GetMapping("/{partnerDomain}")
    public ResponseEntity<PartnerCompanyResponse> getPartnerCompanyResponseByDomain(@PathVariable String partnerDomain) {
        PartnerCompanyResponse partnerCompanyResponse = partnerCompanyService.getPartnerCompanyResponseByDomain(partnerDomain);

        return ResponseEntity.ok(partnerCompanyResponse);
    }


    
    @Operation(operationId = "getPartnerCompanyResponseById", summary = "파트너사 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너사 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 ID")
    })
    @GetMapping("/{partnerCompanyId}")
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
    @PutMapping("/{partnerDomain}")
    public ResponseEntity<Void> updatePartnerCompanyByPartnerDomain(@PathVariable String partnerDomain,
                                                                    @Valid @RequestBody PartnerCompanyUpdateRequest request) {
        partnerCompanyService.updateByDomain(partnerDomain, request);

        return ResponseEntity.noContent().build();
    }


    
    @Operation(operationId = "updatePartnerCompanyByPartnerCompanyId", summary = "파트너사 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 ID")
    })
    @PutMapping("/{partnerCompanyId}")
    public ResponseEntity<Void> updatePartnerCompanyByPartnerCompanyId(@PathVariable Long partnerCompanyId,
                                                                       @Valid @RequestBody PartnerCompanyUpdateRequest request) {
        partnerCompanyService.updateById(partnerCompanyId, request);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deletePartnerCompanyByPartnerCompanyId", summary = "파트너사 삭제")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    })
    @DeleteMapping("/{partnerDomain}")
    public ResponseEntity<Void> deletePartnerCompanyByPartnerDomain(@PathVariable String partnerDomain) {
        partnerCompanyService.deleteByDomain(partnerDomain);

        return ResponseEntity.noContent().build();
    }


    
    @Operation(operationId = "deletePartnerCompanyByPartnerDomain", summary = "파트너사 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 Domain")
    })
    @DeleteMapping("/{partnerCompanyId}")
    public ResponseEntity<Void> deletePartnerCompanyById(@PathVariable Long partnerCompanyId) {
        partnerCompanyService.deleteById(partnerCompanyId);

        return ResponseEntity.noContent().build();
    }
}
