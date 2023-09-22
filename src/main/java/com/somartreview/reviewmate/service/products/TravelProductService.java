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


    public TravelProduct findByTravelProductId(String partnerDomain, String partnerCustomId) {
        return travelProductRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(TRAVEL_PRODUCT_NOT_FOUND));
    }
}
