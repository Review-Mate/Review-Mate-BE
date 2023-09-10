package com.somartreview.reviewmate.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelProductUpdateRequest {

    private String thumbnailUrl;

    private String name;


    public TravelProductUpdateRequest(SingleTravelProductUpdateRequest singleTravelProductUpdateRequest, String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        this.name = singleTravelProductUpdateRequest.getName();
    }
}
