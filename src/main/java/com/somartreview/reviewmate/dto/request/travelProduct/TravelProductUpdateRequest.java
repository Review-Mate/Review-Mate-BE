package com.somartreview.reviewmate.dto.request.travelProduct;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelProductUpdateRequest {

    private String thumbnailUrl;

    private String name;

    private TravelProductCategory travelProductCategory;

    public TravelProductUpdateRequest(SingleTravelProductUpdateRequest singleTravelProductUpdateRequest, String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        this.name = singleTravelProductUpdateRequest.getName();
        this.travelProductCategory = singleTravelProductUpdateRequest.getTravelProductCategory();
    }
}
