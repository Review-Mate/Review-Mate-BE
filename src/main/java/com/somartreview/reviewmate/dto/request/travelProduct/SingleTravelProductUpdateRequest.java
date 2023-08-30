package com.somartreview.reviewmate.dto.request.travelProduct;

import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProductCategory;
import com.somartreview.reviewmate.exception.EnumNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductUpdateRequest {

    @NotBlank
    @Schema(description = "상품명", example = "신라더스테이 호텧")
    private String name;

    @EnumNotNull
    @Schema(description = "여행상품 카테고리", example = "ACCOMMODATION")
    private SingleTravelProductCategory singleTravelProductCategory;

}
