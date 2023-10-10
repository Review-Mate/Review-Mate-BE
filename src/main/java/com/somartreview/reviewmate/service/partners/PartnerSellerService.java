package com.somartreview.reviewmate.service.partners;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSellerRepository;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerCreateRequest;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
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
    public Long create(PartnerSellerCreateRequest request) {
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        final PartnerCompany partnerCompany = partnerCompanyService.findByPartnerDomain(request.getPartnerCompanyDomain());

        return partnerSellerRepository.save(request.toEntity(partnerCompany)).getId();
    }

    private void validateUniquePhoneNumber(final String phoneNumber) {
        if (partnerSellerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DomainLogicException(PARTNER_SELLER_NOT_UNIQUE_PHONE_NUMBER);
        }
    }

    private void validateUniqueKakaoId(final String kakaoId) {
        if (partnerSellerRepository.existsByKakaoId(kakaoId)) {
            throw new DomainLogicException(PARTNER_SELLER_NOT_UNIQUE_KAKAO_ID);
        }
    }

    public PartnerSeller findById(Long id) {
        return partnerSellerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_SELLER_NOT_FOUND));
    }

    public PartnerSellerResponse getPartnerSellerResponseByPartnerSellerId(Long partnerSellerId) {

        PartnerSeller partnerSeller = findById(partnerSellerId);
        return new PartnerSellerResponse(partnerSeller);
    }

    @Transactional
    public void update(Long id, PartnerSellerUpdateRequest request) {
        PartnerSeller partnerSeller = findById(id);
        partnerSeller.update(request);
    }
}
