package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompanyRepository;
import com.somartreview.reviewmate.dto.request.partnerCompany.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerCompanyService {

    private final PartnerCompanyRepository partnerCompanyRepository;

    @Transactional
    public Long createPartnerCompany(PartnerCompanyCreateRequest request) {
        return partnerCompanyRepository.save(request.toEntity()).getId();
    }

    public PartnerCompany findPartnerCompanyById(Long partnerCompanyId) {
        return partnerCompanyRepository.findById(partnerCompanyId)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }
}
