package com.somartreview.reviewmate.service.partners;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSellerRepository;
import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerSeller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerSeller.PartnerSellerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
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

    public PartnerSeller findById(Long partnerSellerId) {
        return partnerSellerRepository.findById(partnerSellerId)
                .orElseThrow(() -> new DomainLogicException(PARTNER_SELLER_NOT_FOUND));
    }

    public PartnerSellerResponse getPartnerSellerResponseById(Long id) {

        PartnerSeller partnerSeller = findById(id);
        return new PartnerSellerResponse(partnerSeller);
    }

    @Transactional
    public void updateById(Long id, PartnerSellerUpdateRequest request) {

        PartnerSeller partnerSeller = findById(id);
        partnerSeller.update(request);
    }

    @Transactional
    public void deleteById(Long id) {

        partnerSellerRepository.deleteById(id);
    }
}
