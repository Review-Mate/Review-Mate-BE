package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSellerRepository;
import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerSeller.PartnerSellerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnerSellerService {

    private final PartnerSellerRepository partnerSellerRepository;
    private final PartnerCompanyService partnerCompanyService;

    @Transactional
    public Long createPartnerSeller(PartnerSellerCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyById(request.getPartnerCompanyId());

        return partnerSellerRepository.save(request.toEntity(partnerCompany)).getId();
    }

    public PartnerSeller findPartnerSellerById(Long partnerSellerId) {
        return partnerSellerRepository.findById(partnerSellerId)
                .orElseThrow(() -> new DomainLogicException(ErrorCode.PARTNER_SELLER_NOT_FOUND));
    }

    public PartnerSeller findPartnerSellerByPhoneNumber(String phoneNumber) {
        return partnerSellerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DomainLogicException(ErrorCode.PARTNER_SELLER_NOT_FOUND));
    }

    public PartnerSellerResponse getPartnerSellerResponseById(Long id) {
        PartnerSeller partnerSeller = findPartnerSellerById(id);
        return new PartnerSellerResponse(partnerSeller);
    }

    public PartnerSellerResponse getPartnerSellerResponseByPhoneNumber(String phoneNumber) {
        PartnerSeller partnerSeller = findPartnerSellerByPhoneNumber(phoneNumber);
        return new PartnerSellerResponse(partnerSeller);
    }

    @Transactional
    public void updatePartnerSellerById(Long id, PartnerSellerUpdateRequest request) {
        PartnerSeller partnerSeller = findPartnerSellerById(id);
        partnerSeller.update(request);
    }

    @Transactional
    public void updatePartnerSellerByPhoneNumber(String phoneNumber, PartnerSellerUpdateRequest request) {
        PartnerSeller partnerSeller = findPartnerSellerByPhoneNumber(phoneNumber);
        partnerSeller.update(request);
    }

    @Transactional
    public void deletePartnerSellerById(Long id) {
        partnerSellerRepository.deleteById(id);
    }

    @Transactional
    public void deletePartnerSellerByPhoneNumber(String phoneNumber) {
        partnerSellerRepository.deleteByPhoneNumber(phoneNumber);
    }
}
