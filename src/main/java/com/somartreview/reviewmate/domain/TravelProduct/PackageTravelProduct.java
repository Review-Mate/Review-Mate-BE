package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerSeller;
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

    @OneToMany(mappedBy = "packageTravelProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourCourse> tourCourses = new ArrayList<>();

    public PackageTravelProduct(String clientSideProductId, String thumbnailUrl, String name, Float rating, PartnerCompany partnerCompany, PartnerSeller partnerSeller) {
        super(clientSideProductId, thumbnailUrl, name, rating, partnerCompany, partnerSeller);
    }

    public void addTourCourse(TourCourse tourCourse) {
        this.tourCourses.add(tourCourse);
    }
}
