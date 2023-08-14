package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompanyRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.PARTNET_COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerCompanyService {

    private final PartnerCompanyRepository partnerCompanyRepository;

    public PartnerCompany findPartnerCompanyById(Long partnerCompanyId) {
        return partnerCompanyRepository.findById(partnerCompanyId)
                .orElseThrow(() -> new DomainLogicException(PARTNET_COMPANY_NOT_FOUND));
    }
}
