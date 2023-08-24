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

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PartnerSellerService {

    private final PartnerSellerRepository partnerSellerRepository;
    private final PartnerCompanyService partnerCompanyService;

    @Transactional
    public Long createPartnerSeller(String partnerDomain, PartnerSellerCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyByDomain(partnerDomain);

        return partnerSellerRepository.save(request.toEntity(partnerCompany)).getId();
    }

    public PartnerSeller findPartnerSellerById(Long partnerSellerId) {
        return partnerSellerRepository.findById(partnerSellerId)
                .orElseThrow(() -> new DomainLogicException(PARTNER_SELLER_NOT_FOUND));
    }

    public PartnerSeller findPartnerSellerByPhoneNumber(String phoneNumber) {
        return partnerSellerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DomainLogicException(PARTNER_SELLER_NOT_FOUND));
    }

    public PartnerSellerResponse getPartnerSellerResponseById(String partnerDomain, Long id) {
        validatePartnerSellerWithPartnerDomain(id, partnerDomain);

        PartnerSeller partnerSeller = findPartnerSellerById(id);
        return new PartnerSellerResponse(partnerSeller);
    }

    public PartnerSellerResponse getPartnerSellerResponseByPhoneNumber(String partnerDomain, String phoneNumber) {
        validatePartnerSellerWithPartnerDomain(phoneNumber, partnerDomain);

        PartnerSeller partnerSeller = findPartnerSellerByPhoneNumber(phoneNumber);
        return new PartnerSellerResponse(partnerSeller);
    }

    @Transactional
    public void updatePartnerSellerById(String partnerDomain, Long id, PartnerSellerUpdateRequest request) {
        validatePartnerSellerWithPartnerDomain(id, partnerDomain);

        PartnerSeller partnerSeller = findPartnerSellerById(id);
        partnerSeller.update(request);
    }

    @Transactional
    public void updatePartnerSellerByPhoneNumber(String partnerDomain, String phoneNumber, PartnerSellerUpdateRequest request) {
        validatePartnerSellerWithPartnerDomain(phoneNumber, partnerDomain);

        PartnerSeller partnerSeller = findPartnerSellerByPhoneNumber(phoneNumber);
        partnerSeller.update(request);
    }

    @Transactional
    public void deletePartnerSellerById(String partnerDomain, Long id) {
        validatePartnerSellerWithPartnerDomain(id, partnerDomain);
        
        partnerSellerRepository.deleteById(id);
    }

    @Transactional
    public void deletePartnerSellerByPhoneNumber(String partnerDomain, String phoneNumber) {
        validatePartnerSellerWithPartnerDomain(phoneNumber, partnerDomain);

        partnerSellerRepository.deleteByPhoneNumber(phoneNumber);
    }

    public void validatePartnerSellerWithPartnerDomain(Long partnerSellerId, String partnerDomain) {
        if (!partnerSellerRepository.existsByIdAndPartnerCompany_Domain(partnerSellerId, partnerDomain)) {
            throw new DomainLogicException(PARTNER_SELLER_NOT_MATCH_WITH_PARTNER_DOMAIN);
        }
    }

    public void validatePartnerSellerWithPartnerDomain(String partnerSellerPhoneNumber, String partnerDomain) {
        if (!partnerSellerRepository.existsByPhoneNumberAndPartnerCompany_Domain(partnerSellerPhoneNumber, partnerDomain)) {
            throw new DomainLogicException(PARTNER_SELLER_NOT_MATCH_WITH_PARTNER_DOMAIN);
        }
    }
}
