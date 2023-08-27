package com.somartreview.reviewmate.dto.request.travelProduct;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProductCategory;
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
    @Schema(description = "상품명", example = "신라더스테이 호텧")
    private String name;

    @NotBlank
    @Schema(description = "여행상품 카테고리", example = "ACCOMMODATION")
    private TravelProductCategory travelProductCategory;

    @NotNull
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 시작시간", example = "2023.08.14T15:00")
    private LocalDateTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 종료시간", example = "2023.08.15T12:00")
    private LocalDateTime endTime;

}
