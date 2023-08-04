package com.somartreview.reviewmate.domain.travelProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SingleTravelProduct extends TravelProduct {

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Category category;

    public SingleTravelProduct(String clientSideProductId, String thumbnailUrl, String name, Float rating, LocalDateTime startTime, LocalDateTime endTime, Category category) {
        super(clientSideProductId, thumbnailUrl, name, rating);
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }
}
