package com.somartreview.reviewmate.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRatingCountsDto {

    private long oneStarRatingCount = 0L;
    private long twoStarRatingCount = 0L;
    private long threeStarRatingCount = 0L;
    private long fourStarRatingCount = 0L;
    private long fiveStarRatingCount = 0L;
}
