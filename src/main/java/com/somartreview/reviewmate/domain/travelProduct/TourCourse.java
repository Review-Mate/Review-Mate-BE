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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id")
    private PackageTravelProduct packageTravelProduct;
}
