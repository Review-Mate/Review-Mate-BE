package com.somartreview.reviewmate.domain.travelProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class SingleTravelProduct extends TravelProduct {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Category category;
}
