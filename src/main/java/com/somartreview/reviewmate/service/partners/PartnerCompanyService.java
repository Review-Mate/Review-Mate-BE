package com.somartreview.reviewmate.service.partners;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompanyRepository;
import com.somartreview.reviewmate.dto.request.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.dto.request.partnerCompany.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.response.partnerCompany.PartnerCompanyResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PartnerCompanyService {

    private final PartnerCompanyRepository partnerCompanyRepository;

    @Transactional
    public String create(PartnerCompanyCreateRequest request) {
        validateNotExistDomain(request.getDomain());

        return partnerCompanyRepository.save(request.toEntity()).getDomain();
    }

    public void validateNotExistDomain(String domain) {
        if (partnerCompanyRepository.existsByDomain(domain)) {
            throw new DomainLogicException(PARTNER_COMPANY_EXIST_DOMAIN);
        }
    }

    public PartnerCompany findById(Long id) {
        return partnerCompanyRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompany findByDomain(String domain) {
        return partnerCompanyRepository.findByDomain(domain)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompanyResponse getPartnerCompanyResponseById(Long id) {
        PartnerCompany partnerCompany = findById(id);
        return new PartnerCompanyResponse(partnerCompany);
    }

    public PartnerCompanyResponse getPartnerCompanyResponseByDomain(String domain) {
        PartnerCompany partnerCompany = findByDomain(domain);
        return new PartnerCompanyResponse(partnerCompany);
    }

    @Transactional
    public void updateById(Long id, PartnerCompanyUpdateRequest request) {
        PartnerCompany partnerCompany = findById(id);

        validateDuplicatedDomain(partnerCompany.getDomain(), request.getDomain());

        partnerCompany.update(request);
    }

    @Transactional
    public void updateByDomain(String domain, PartnerCompanyUpdateRequest request) {
        PartnerCompany partnerCompany = findByDomain(domain);

        validateDuplicatedDomain(partnerCompany.getDomain(), request.getDomain());

        partnerCompany.update(request);
    }

    public void validateDuplicatedDomain(String oldDomain, String newDomain) {
        if (!oldDomain.equals(newDomain) && partnerCompanyRepository.existsByDomain(newDomain)) {
            throw new DomainLogicException(PARTNER_COMPANY_EXIST_DOMAIN);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        partnerCompanyRepository.deleteById(id);
    }

    @Transactional
    public void deleteByDomain(String domain) {
        partnerCompanyRepository.deleteByDomain(domain);
    }

    public void validateExistPartnerDomain(String partnerDomain) {
        if (!partnerCompanyRepository.existsByDomain(partnerDomain)) {
            throw new DomainLogicException(NOT_EXIST_PARTNER_DOMAIN);
        }
    }
}
