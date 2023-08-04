package com.somartreview.reviewmate.domain.travelProduct;

import com.somartreview.reviewmate.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class TourCourse extends BaseEntity {

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

    public TourCourse(String name, LocalDateTime startTime, LocalDateTime endTime, PackageTravelProduct packageTravelProduct) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;

        packageTravelProduct.addTourCourse(this);
        this.packageTravelProduct = packageTravelProduct;
    }
}
