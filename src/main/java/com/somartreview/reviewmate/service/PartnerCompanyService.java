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
    public String createPartnerCompany(PartnerCompanyCreateRequest request) {
        validateDuplicatedDomain(request.getDomain());

        return partnerCompanyRepository.save(request.toEntity()).getDomain();
    }

    public void validateDuplicatedDomain(String domain) {
        if (partnerCompanyRepository.existsByDomain(domain)) {
            throw new DomainLogicException(PARTNER_COMPANY_DUPLICATED_DOMAIN);
        }
    }

    public PartnerCompany findPartnerCompanyById(Long id) {
        return partnerCompanyRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompany findPartnerCompanyByDomain(String domain) {
        return partnerCompanyRepository.findByDomain(domain)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompanyResponse getPartnerCompanyResponseById(Long id) {
        PartnerCompany partnerCompany = findPartnerCompanyById(id);
        return new PartnerCompanyResponse(partnerCompany);
    }

    public PartnerCompanyResponse getPartnerCompanyResponseByDomain(String domain) {
        PartnerCompany partnerCompany = findPartnerCompanyByDomain(domain);
        return new PartnerCompanyResponse(partnerCompany);
    }

    @Transactional
    public void updatePartnerCompanyById(Long id, PartnerCompanyUpdateRequest request) {
        validateDuplicatedDomain(request.getDomain());

        PartnerCompany partnerCompany = findPartnerCompanyById(id);
        partnerCompany.update(request);
    }

    @Transactional
    public void updatePartnerCompanyByDomain(String domain, PartnerCompanyUpdateRequest request) {
        validateDuplicatedDomain(request.getDomain());

        PartnerCompany partnerCompany = findPartnerCompanyByDomain(domain);
        partnerCompany.update(request);
    }

    @Transactional
    public void deletePartnerCompanyById(Long id) {
        partnerCompanyRepository.deleteById(id);
    }

    @Transactional
    public void deletePartnerCompanyByDomain(String domain) {
        partnerCompanyRepository.deleteByDomain(domain);
    }
}
