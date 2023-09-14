package com.somartreview.reviewmate.service.products;

import com.somartreview.reviewmate.domain.product.TravelProduct;
import com.somartreview.reviewmate.domain.product.TravelProductRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TravelProductService {

    private final TravelProductRepository travelProductRepository;


    public void validateExistTravelProduct(Long travelProductId) {
        if (!travelProductRepository.existsById(travelProductId)) {
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
        }
    }

    public void validateExistTravelProduct(String partnerDomain, String partnerCustomId) {
        if (!travelProductRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)) {
            throw new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND);
        }
    }

    public TravelProduct findByTravelProductId(String partnerDomain, String partnerCustomId) {
        return travelProductRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }

    public void deleteAllByPartnerDomain(String partnerDomain) {
        travelProductRepository.deleteAllByPartnerCompany_PartnerDomain(partnerDomain);
    }
}
