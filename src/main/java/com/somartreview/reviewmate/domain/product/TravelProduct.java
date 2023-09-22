package com.somartreview.reviewmate.domain.product;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import com.somartreview.reviewmate.dto.product.TravelProductUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
public abstract class TravelProduct extends BaseEntity {

    private static final int MAX_PARTNER_CUSTOM_ID_LENGTH = 50;
    private static final int MAX_THUMBNAIL_URL_LENGTH = 1024;
    private static final int MAX_NAME_LENGTH = 255;



    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String partnerCustomId;

    @Column(length = 1024)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float rating = 0.0f;

    @Column(nullable = false)
    private Long reviewCount = 0L;

    @Column(nullable = false)
    private Integer fiveStarRatingCount = 0;

    @Column(nullable = false)
    private Integer fourStarRatingCount = 0;

    @Column(nullable = false)
    private Integer threeStarRatingCount = 0;

    @Column(nullable = false)
    private Integer twoStarRatingCount = 0;

    @Column(nullable = false)
    private Integer oneStarRatingCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_company_id", nullable = false)
    private PartnerCompany partnerCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_seller_id", nullable = false)
    private PartnerSeller partnerSeller;


    protected TravelProduct(String partnerCustomId, String thumbnailUrl, String name, PartnerCompany partnerCompany, PartnerSeller partnerSeller) {
        validatePartnerCustomId(partnerCustomId);
        this.partnerCustomId = partnerCustomId;
        validateThumbnailUrl(thumbnailUrl);
        this.thumbnailUrl = thumbnailUrl;
        validateName(name);
        this.name = name;
        this.partnerCompany = partnerCompany;
        this.partnerSeller = partnerSeller;
    }

    public void update(TravelProductUpdateRequest travelProductUpdateRequest) {
        validateThumbnailUrl(travelProductUpdateRequest.getThumbnailUrl());
        this.thumbnailUrl = travelProductUpdateRequest.getThumbnailUrl();
        validateName(travelProductUpdateRequest.getName());
        this.name = travelProductUpdateRequest.getName();
    }

    private void validatePartnerCustomId(final String partnerCustomerId) {
        if (partnerCustomerId.isBlank() || partnerCustomerId.length() > MAX_PARTNER_CUSTOM_ID_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_PARTNER_CUSTOM_ID_ERROR);
        }
    }

    private void validateThumbnailUrl(final String thumbnailUrl) {
        if (thumbnailUrl == null) {
            return;
        }

        if (thumbnailUrl.isBlank() || thumbnailUrl.length() > MAX_THUMBNAIL_URL_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR);
        }
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_NAME_ERROR);
        }
    }


    public void updateReviewData(int newReviewRating) {
        this.reviewCount++;
        this.rating = (this.rating * (this.reviewCount - 1) + newReviewRating) / this.reviewCount;

        if (newReviewRating == 5) fiveStarRatingCount++;
        else if (newReviewRating == 4) fourStarRatingCount++;
        else if (newReviewRating == 3) threeStarRatingCount++;
        else if (newReviewRating == 2) twoStarRatingCount++;
        else if (newReviewRating == 1) oneStarRatingCount++;
    }

    public void removeReviewData(int removedReviewRating) {
        this.reviewCount--;
        if (reviewCount == 0) {
            this.rating = 0.0f;
            return;
        }

        if (removedReviewRating == 5) fiveStarRatingCount--;
        else if (removedReviewRating == 4) fourStarRatingCount--;
        else if (removedReviewRating == 3) threeStarRatingCount--;
        else if (removedReviewRating == 2) twoStarRatingCount--;
        else if (removedReviewRating == 1) oneStarRatingCount--;

        this.rating = (this.rating * (this.reviewCount + 1) - removedReviewRating) / this.reviewCount;
    }
}
