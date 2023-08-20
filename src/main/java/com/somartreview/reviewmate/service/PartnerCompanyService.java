package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompanyRepository;
import com.somartreview.reviewmate.dto.request.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.dto.request.partnerCompany.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.response.partnerCompany.PartnerCompanyResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_COMPANY_DUPLICATED_DOMAIN;
import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerCompanyService {

    private final PartnerCompanyRepository partnerCompanyRepository;

    @Transactional
    public Long createPartnerCompany(PartnerCompanyCreateRequest request) {
        validateDuplicatedDomain(request.getDomain());

        return partnerCompanyRepository.save(request.toEntity()).getId();
    }

    public void validateDuplicatedDomain(String domain) {
        if (partnerCompanyRepository.existsByDomain(domain)) {
            throw new DomainLogicException(PARTNER_COMPANY_DUPLICATED_DOMAIN);
        }
    }

    public PartnerCompany findPartnerCompanyById(Long partnerCompanyId) {
        return partnerCompanyRepository.findById(partnerCompanyId)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompany findPartnerCompanyByDomain(String domain) {
        return partnerCompanyRepository.findByDomain(domain)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompanyResponse getPartnerCompanyResponseById(Long partnerCompanyId) {
        PartnerCompany partnerCompany = findPartnerCompanyById(partnerCompanyId);
        return new PartnerCompanyResponse(partnerCompany);
    }

    public PartnerCompanyResponse getPartnerCompanyResponseByDomain(String domain) {
        PartnerCompany partnerCompany = findPartnerCompanyByDomain(domain);
        return new PartnerCompanyResponse(partnerCompany);
    }

    @Transactional
    public void updatePartnerCompany(Long partnerCompanyId, PartnerCompanyUpdateRequest request) {
        validateDuplicatedDomain(request.getDomain());

        PartnerCompany partnerCompany = findPartnerCompanyById(partnerCompanyId);
        partnerCompany.update(request);
    }

    @Transactional
    public void deletePartnerCompany(Long partnerCompanyId) {
        partnerCompanyRepository.deleteById(partnerCompanyId);
    }
}
