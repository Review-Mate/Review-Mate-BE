package com.somartreview.reviewmate.service.partners;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompanyRepository;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyResponse;
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
        validateUniquePartnerDomain(request.getPartnerDomain());

        return partnerCompanyRepository.save(request.toEntity()).getPartnerDomain();
    }

    private void validateUniquePartnerDomain(String domain) {
        if (partnerCompanyRepository.existsByPartnerDomain(domain)) {
            throw new DomainLogicException(PARTNER_COMPANY_NOT_UNIQUE_PARTNER_DOMAIN);
        }
    }

    public PartnerCompany findByPartnerDomain(String domain) {
        return partnerCompanyRepository.findByPartnerDomain(domain)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompanyResponse getPartnerCompanyResponseByPartnerDomain(String domain) {
        PartnerCompany partnerCompany = findByPartnerDomain(domain);
        return new PartnerCompanyResponse(partnerCompany);
    }

    @Transactional
    public void update(String domain, PartnerCompanyUpdateRequest request) {
        PartnerCompany partnerCompany = findByPartnerDomain(domain);

        validateDuplicatedPartnerDomain(partnerCompany.getPartnerDomain(), request.getPartnerDomain());

        partnerCompany.update(request);
    }

    private void validateDuplicatedPartnerDomain(String oldDomain, String newDomain) {
        if (!oldDomain.equals(newDomain) && partnerCompanyRepository.existsByPartnerDomain(newDomain)) {
            throw new DomainLogicException(PARTNER_COMPANY_DUPLICATED_PARTNER_DOMAIN);
        }
    }

    @Transactional
    public void delete(String domain) {
        partnerCompanyRepository.deleteByPartnerDomain(domain);
    }
}
