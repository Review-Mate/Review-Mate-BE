package com.somartreview.reviewmate.domain.travelProduct;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PackageTravelProduct extends TravelProduct {

    @OneToMany(mappedBy = "packageTravelProduct", cascade = CascadeType.ALL)
    private List<TourCourse> tourCourses = new ArrayList<>();

    public PackageTravelProduct(String clientSideProductId, String thumbnailUrl, String name, Float rating) {
        super(clientSideProductId, thumbnailUrl, name, rating);
    }
}
