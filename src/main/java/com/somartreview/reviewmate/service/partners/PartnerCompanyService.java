package com.somartreview.reviewmate.service.partners;

import com.somartreview.reviewmate.domain.partnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.partnerCompany.PartnerCompanyRepository;
import com.somartreview.reviewmate.dto.request.partnerCompany.PartnerCompanyUpdateRequest;
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
        validateUniquePartnerDomain(request.getPartnerDomain());

        return partnerCompanyRepository.save(request.toEntity()).getPartnerDomain();
    }

    public void validateUniquePartnerDomain(String partnerDomain) {
        if (partnerCompanyRepository.existsByPartnerDomain(partnerDomain)) {
            throw new DomainLogicException(PARTNER_COMPANY_NOT_UNIQUE_PARTNER_DOMAIN);
        }
    }

    public PartnerCompany findById(Long id) {
        return partnerCompanyRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompany findByPartnerDomain(String partnerDomain) {
        return partnerCompanyRepository.findByPartnerDomain(partnerDomain)
                .orElseThrow(() -> new DomainLogicException(PARTNER_COMPANY_NOT_FOUND));
    }

    public PartnerCompanyResponse getPartnerCompanyResponseByDomain(String partnerDomain) {
        PartnerCompany partnerCompany = findByPartnerDomain(partnerDomain);
        return new PartnerCompanyResponse(partnerCompany);
    }

    @Transactional
    public void updateByPartnerDomain(String partnerDomain, PartnerCompanyUpdateRequest request) {
        PartnerCompany partnerCompany = findByPartnerDomain(partnerDomain);

        validateDuplicatedPartnerDomain(partnerCompany.getPartnerDomain(), request.getPartnerDomain());

        partnerCompany.update(request);
    }

    public void validateDuplicatedPartnerDomain(String oldDomain, String newDomain) {
        if (!oldDomain.equals(newDomain) && partnerCompanyRepository.existsByPartnerDomain(newDomain)) {
            throw new DomainLogicException(PARTNER_COMPANY_DUPLICATED_PARTNER_DOMAIN);
        }
    }

    @Transactional
    public void deleteByPartnerDomain(String partnerDomain) {
        partnerCompanyRepository.deleteByPartnerDomain(partnerDomain);
    }
}
