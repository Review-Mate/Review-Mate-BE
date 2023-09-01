package com.somartreview.reviewmate.web.partners;

import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerManager.PartnerManagerResponse;
import com.somartreview.reviewmate.service.partners.PartnerManagerService;
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

@Tag(name = "파트너사 관리자")
@RestController
@RequestMapping("/api/console/v1/managers")
@RequiredArgsConstructor
public class PartnerManagerController {

    private final PartnerManagerService partnerManagerService;


    @Operation(operationId = "createPartnerManager", summary = "파트너사 관리자 생성")
    @ApiResponse(responseCode = "201", description = "파트너사 관리자 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 파트너사 관리자의 URI, /api/v1/{partnerDomain}/managers/{partnerManagerId}", schema = @Schema(type = "string"))
    })
    @PostMapping("/")
    public ResponseEntity<Void> createPartnerManager(@Valid @RequestBody PartnerManagerCreateRequest partnerManagerCreateRequest) {
        Long partnerManagerId = partnerManagerService.create(partnerManagerCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/console//managers/" + partnerManagerId)).build();
    }


    @Operation(operationId = "getPartnerManagerResponseById", summary = "파트너사 관리자 조회")
    @Parameter(name = "partnerManagerId", description = "조회 할 관리자 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너사 관리자 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 관리자 ID"),
    })
    @GetMapping("/{partnerManagerId}")
    public ResponseEntity<PartnerManagerResponse> getPartnerManagerResponseById(@PathVariable Long partnerManagerId) {
        PartnerManagerResponse partnerManagerResponse = partnerManagerService.getPartnerManagerResponseById(partnerManagerId);

        return ResponseEntity.ok(partnerManagerResponse);
    }


    @Operation(operationId = "updatePartnerManagerById", summary = "파트너사 관리자 정보 수정")
    @Parameter(name = "partnerManagerId", description = "수정 할 관리자 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 관리자 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 관리자 ID"),
    })
    @PutMapping("/{partnerManagerId}")
    public ResponseEntity<Void> updatePartnerManagerById(@PathVariable Long partnerManagerId,
                                                         @Valid @RequestBody PartnerManagerUpdateRequest partnerManagerUpdateRequest) {
        partnerManagerService.updateById(partnerManagerId, partnerManagerUpdateRequest);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deletePartnerManagerById", summary = "파트너사 관리자 삭제")
    @Parameter(name = "partnerManagerId", description = "삭제 할 관리자 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파트너사 관리자 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 관리자 ID"),
    })
    @DeleteMapping("/{partnerManagerId}")
    public ResponseEntity<Void> deletePartnerManagerById(@PathVariable Long partnerManagerId) {
        partnerManagerService.deleteById(partnerManagerId);

        return ResponseEntity.noContent().build();
    }
}
