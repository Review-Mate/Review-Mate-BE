package com.somartreview.reviewmate.web.partners.console;

import com.somartreview.reviewmate.dto.partner.console.PartnerConsoleConfigUpdateRequest;
import com.somartreview.reviewmate.service.partners.console.PartnerConsoleConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "관리자 콘솔")
@RestController
@RequestMapping("/api/console/v1/")
@RequiredArgsConstructor
public class PartnerConsoleController {

    private final PartnerConsoleConfigService partnerConsoleConfigService;


    @Operation(operationId = "updatePartnerConsoleConfig", summary = "관리자 콘솔 설정 정보 수정")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "204", description = "관리자 콘솔 설정 정보 수정 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @PutMapping("{partnerDomain}/config")
    public ResponseEntity<Void> updatePartnerConsoleConfig(@PathVariable String partnerDomain,
                                                           @Valid @RequestBody PartnerConsoleConfigUpdateRequest request) {
        partnerConsoleConfigService.update(partnerDomain, request);

        return ResponseEntity.noContent().build();
    }
}
