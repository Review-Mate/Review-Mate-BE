package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerManager.PartnerManagerResponse;
import com.somartreview.reviewmate.service.PartnerManagerService;
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

@Tag(name = "파트너사 관리자")
@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
public class PartnerManagerController {

    private final PartnerManagerService partnerManagerService;


    @Operation(operationId = "createPartnerManager", summary = "파트너사 관리자 생성")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "201", description = "파트너사 관리자 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 파트너사 관리자의 URI, /api/v1/{partnerDomain}/managers/{partnerManagerId}", schema = @Schema(type = "string"))
    })
    @PostMapping("{partnerDomain}/managers")
    public ResponseEntity<Void> createPartnerManager(@PathVariable String partnerDomain,
                                                     @Valid @RequestBody PartnerManagerCreateRequest partnerManagerCreateRequest) {
        Long partnerManagerId = partnerManagerService.createPartnerManager(partnerDomain, partnerManagerCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/" + partnerDomain + "/managers/" + partnerManagerId)).build();
    }


    @Operation(operationId = "getPartnerManagerResponseById", summary = "파트너사 관리자 조회")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerManagerId", description = "조회 할 관리자 ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너사 관리자 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 관리자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 관리자가 소속된 도메인이 다름")
    })
    @GetMapping("{partnerDomain}/managers/{partnerManagerId}")
    public ResponseEntity<PartnerManagerResponse> getPartnerManagerResponseById(@PathVariable String partnerDomain,
                                                                                @PathVariable Long partnerManagerId) {
        PartnerManagerResponse partnerManagerResponse = partnerManagerService.getPartnerManagerResponseById(partnerDomain, partnerManagerId);

        return ResponseEntity.ok(partnerManagerResponse);
    }


    @Operation(operationId = "updatePartnerManagerById", summary = "파트너사 관리자 정보 수정")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerManagerId", description = "수정 할 관리자 ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 관리자 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 관리자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 관리자가 소속된 도메인이 다름")
    })
    @PutMapping("{partnerDomain}/managers/{partnerManagerId}")
    public ResponseEntity<Void> updatePartnerManagerById(@PathVariable String partnerDomain,
                                                         @PathVariable Long partnerManagerId,
                                                         @Valid @RequestBody PartnerManagerUpdateRequest partnerManagerUpdateRequest) {
        partnerManagerService.updatePartnerManagerById(partnerDomain, partnerManagerId, partnerManagerUpdateRequest);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deletePartnerManagerById", summary = "파트너사 관리자 삭제")
    @Parameters({
            @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr"),
            @Parameter(name = "partnerManagerId", description = "삭제 할 관리자 ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 관리자 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 관리자 ID"),
            @ApiResponse(responseCode = "400", description = "파트너사 도메인과 관리자가 소속된 도메인이 다름")
    })
    @DeleteMapping("{partnerDomain}/managers/{partnerManagerId}")
    public ResponseEntity<Void> deletePartnerManagerById(@PathVariable String partnerDomain,
                                                         @PathVariable Long partnerManagerId) {
        partnerManagerService.deletePartnerManagerById(partnerDomain, partnerManagerId);

        return ResponseEntity.noContent().build();
    }
}
