package com.somartreview.reviewmate.dto.request.review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Review.Review;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    @Schema(description = "리뷰 평점 (1 ~ 5)", example = "5")
    @NotNull
    private Integer rating;

    @Schema(description = "리뷰 제목")
    @NotBlank
    private String title;

    @Schema(description = "리뷰 내용")
    @NotBlank
    private String content;

    public Review toEntity(final Reservation reservation) {
        return Review.builder()
                .rating(rating)
                .title(title)
                .content(content)
                .reservation(reservation)
                .build();
    }
}
