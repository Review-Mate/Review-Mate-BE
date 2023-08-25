package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class TravelProductId implements Serializable {

    private static final int MAX_PARTNER_CUSTOM_ID_LENGTH = 50;
    private static final int MAX_PARTNER_DOMAIN_LENGTH = 50;


    @Column(name = "travel_product_partner_custom_id", nullable = false, length = 50)
    String partnerCustomId;

    @Column(name = "travel_product_partner_domain", nullable = false, length = 50)
    String partnerDomain;

    @Builder
    public TravelProductId(String partnerCustomId, String partnerDomain) {
        validatePartnerCustomId(partnerCustomId);
        this.partnerCustomId = partnerCustomId;
        validatePartnerDomain(partnerDomain);
        this.partnerDomain = partnerDomain;
    }

    private void validatePartnerCustomId(final String partnerCustomerId) {
        if (partnerCustomerId.isBlank() || partnerCustomerId.length() > MAX_PARTNER_CUSTOM_ID_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_PARTNER_CUSTOM_ID_ERROR);
        }
    }

    private void validatePartnerDomain(final String partnerDomain) {
        if (partnerDomain.isBlank() || partnerDomain.length() > MAX_PARTNER_DOMAIN_LENGTH) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_PARTNER_DOMAIN_ERROR);
        }
    }

    public void update(String partnerCustomId) {
        validatePartnerCustomId(partnerCustomId);
        this.partnerCustomId = partnerCustomId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelProductId that = (TravelProductId) o;
        return Objects.equals(getPartnerCustomId(), that.getPartnerCustomId()) && Objects.equals(getPartnerDomain(), that.getPartnerDomain());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPartnerCustomId(), getPartnerDomain());
    }
}
