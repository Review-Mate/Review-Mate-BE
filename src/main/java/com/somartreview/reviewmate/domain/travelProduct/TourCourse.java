package com.somartreview.reviewmate.domain.travelProduct;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class TourCourse {

    @Id @GeneratedValue
    @Column(name = "tour_course_id")
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id")
    private PackageTravelProduct packageTravelProduct;
}
