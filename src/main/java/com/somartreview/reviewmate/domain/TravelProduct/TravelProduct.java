package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.LiveFeedback.LiveFeedback;
import com.somartreview.reviewmate.domain.LiveSatisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Review.Review;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor
public abstract class TravelProduct extends BaseEntity {

    private static final int MAX_THUMBNAIL_URL_LENGTH = 1024;
    private static final int MAX_NAME_LENGTH = 255;


    @Id @GeneratedValue
    @Column(name = "travel_product_id")
    private Long id;

    @Column(nullable = false)
    private String clientSideProductId;

    private String thumbnailUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float rating = 0.0f;

    @OneToMany(mappedBy = "travelProduct")
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_company_id", nullable = false)
    private PartnerCompany partnerCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_seller_id", nullable = false)
    private PartnerSeller partnerSeller;


    public TravelProduct(String clientSideProductId, String thumbnailUrl, String name, Float rating, PartnerCompany partnerCompany, PartnerSeller partnerSeller) {
        this.clientSideProductId = clientSideProductId;
        validateThumbnailUrl(thumbnailUrl);
        this.thumbnailUrl = thumbnailUrl;
        validateName(name);
        this.name = name;
        this.rating = rating;
        this.partnerCompany = partnerCompany;
        this.partnerSeller = partnerSeller;
    }

    private void validateThumbnailUrl(final String thumbnailUrl) {
        if (thumbnailUrl.isBlank() || thumbnailUrl.length() > MAX_THUMBNAIL_URL_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR);
        }
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_NAME_ERROR);
        }
    }

    public void addReview(Review review) {
        int reviewCount = this.reviews.size();
        rating = (rating * reviewCount + review.getRating()) / (reviewCount + 1);

        this.reviews.add(review);
    }
}
