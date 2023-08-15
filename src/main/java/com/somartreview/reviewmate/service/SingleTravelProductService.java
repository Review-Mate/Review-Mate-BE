package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProductRepository;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SingleTravelProductService {

    private final SingleTravelProductRepository singleTravelProductRepository;
    private final PartnerCompanyService partnerCompanyService;
    private final PartnerSellerService partnerSellerService;

    @Transactional
    public Long createSingleTravelProduct(SingleTravelProductCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyById(request.getPartnerCompanyId());
        final PartnerSeller partnerSeller = partnerSellerService.findPartnerSellerById(request.getPartnerSellerId());

        return singleTravelProductRepository.save(request.toEntity(partnerCompany, partnerSeller)).getId();
    }
}
