package com.somartreview.reviewmate.service.partners.seller;

import com.somartreview.reviewmate.domain.partner.seller.PartnerSellerRepository;
import com.somartreview.reviewmate.service.products.TravelProductDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnerSellerDeleteService {

    private final PartnerSellerRepository partnerSellerRepository;
    private final TravelProductDeleteService travelProductDeleteService;


    @Transactional
    public void delete(Long id) {
        travelProductDeleteService.deleteAllByPartnerSellerId(id);
        partnerSellerRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByPartnerDomain(String partnerDomain) {
        partnerSellerRepository.findAllByPartnerCompany_PartnerDomain(partnerDomain)
                .forEach(partnerSeller -> delete(partnerSeller.getId()));
    }
}
