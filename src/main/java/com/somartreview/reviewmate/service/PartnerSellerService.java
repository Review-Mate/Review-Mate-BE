package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSellerRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerSellerService {

    private final PartnerSellerRepository partnerSellerRepository;

    public PartnerSeller findPartnerSellerById(Long partnerSellerId) {
        return partnerSellerRepository.findById(partnerSellerId)
                .orElseThrow(() -> new DomainLogicException(ErrorCode.PARTNER_SELLER_NOT_FOUND));
    }
}
