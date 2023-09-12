package com.somartreview.reviewmate.domain.product;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class TourCourse extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 255;


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_course_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private PackageTravelProduct packageTravelProduct;

    @Builder
    public TourCourse(String name, LocalDateTime startTime, LocalDateTime endTime, PackageTravelProduct packageTravelProduct) {
        validateName(name);
        this.name = name;
        validateTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;

        packageTravelProduct.addTourCourse(this);
        this.packageTravelProduct = packageTravelProduct;
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_NAME_ERROR);
        }
    }

    private void validateTime(final LocalDateTime startTime, final LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_START_TIME_ERROR);
        }
    }
}
