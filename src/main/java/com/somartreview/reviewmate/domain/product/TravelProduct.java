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
    private Integer reviewCount = 0;

    @Column(nullable = false)
    private Float rating = 0.0f;

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
        if (thumbnailUrl.isBlank() || thumbnailUrl.length() > MAX_THUMBNAIL_URL_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR);
        }
    }

    private void validateName(final String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_NAME_ERROR);
        }
    }


    public void addReview(int newReviewRating) {
        this.reviewCount++;
        this.rating = (this.rating * (this.reviewCount - 1) + newReviewRating) / this.reviewCount;
    }

    public void removeReview(int removedReviewRating) {
        this.reviewCount--;
        if (reviewCount == 0) {
            this.rating = 0.0f;
            return;
        }

        this.rating = (this.rating * (this.reviewCount + 1) - removedReviewRating) / this.reviewCount;
    }
}
