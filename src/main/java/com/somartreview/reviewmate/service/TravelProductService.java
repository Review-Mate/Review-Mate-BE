package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProductRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TravelProductService {

    private final TravelProductRepository travelProductRepository;

    public TravelProduct findTravelProductByPartnerDomainAndTravelProductId(String partnerDomain, Long travelProductId) {
        validateTravelProductWithPartnerDomain(partnerDomain, travelProductId);

        return travelProductRepository.findById(travelProductId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }

    public void validateTravelProductWithPartnerDomain(String partnerDomain, Long travelProductId) {
        if (!travelProductRepository.existsByPartnerCompany_DomainAndId(partnerDomain, travelProductId)) {
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_MATCH_WITH_DOMAIN);
        }
    }
}
