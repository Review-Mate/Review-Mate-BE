package com.somartreview.reviewmate.dto.request.travelProduct;

import com.somartreview.reviewmate.domain.TravelProduct.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductUpdateRequest {

    @NotBlank
    @Schema(description = "클라이언트 서비스에서의 상품 식별자\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.", example = "PRODUCT-0001")
    private String partnerSingleTravelProductId;

    @NotBlank
    @Schema(description = "상품명\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.", example = "신라더스테이 호텧")
    private String name;

    @NotBlank
    @Schema(description = "여행상품 카테고리\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.", example = "ACCOMMODATION")
    private Category category;

    @NotNull
    @Schema(description = "파트너사 ID\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.")
    private Long partnerCompanyId;

    @NotNull
    @Schema(description = "파트너사의 판매자 ID\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.")
    private Long partnerSellerId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 시작시간\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.", example = "2023.08.14T15:00")
    private LocalDateTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 종료시간\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.", example = "2023.08.15T12:00")
    private LocalDateTime endTime;

}
