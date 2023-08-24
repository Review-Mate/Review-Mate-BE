package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManager;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManagerRepository;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerManager.PartnerManagerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerManagerService {

    private final PartnerManagerRepository partnerManagerRepository;
    private final PartnerCompanyService partnerCompanyService;

    @Transactional
    public Long createPartnerManager(String partnerDomain, PartnerManagerCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyByDomain(partnerDomain);

        return partnerManagerRepository.save(request.toEntity(partnerCompany)).getId();
    }

    public PartnerManager findPartnerManagerById(Long id) {
        return partnerManagerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_MANAGER_NOT_FOUND));
    }

    public PartnerManagerResponse getPartnerManagerResponseById(String partnerDomain, Long id) {
        validatePartnerManagerWithPartnerDomain(id, partnerDomain);
        final PartnerManager partnerManager = findPartnerManagerById(id);

        return new PartnerManagerResponse(partnerManager);
    }

    @Transactional
    public void updatePartnerManagerById(String partnerDomain, Long id, PartnerManagerUpdateRequest request) {
        validatePartnerManagerWithPartnerDomain(id, partnerDomain);

        PartnerManager partnerManager = findPartnerManagerById(id);
        partnerManager.update(request);
    }

    @Transactional
    public void deletePartnerManagerById(String partnerDomain, Long id) {
        validatePartnerManagerWithPartnerDomain(id, partnerDomain);

        PartnerManager partnerManager = findPartnerManagerById(id);
        partnerManagerRepository.delete(partnerManager);
    }

    public void validatePartnerManagerWithPartnerDomain(Long partnerManagerId, String partnerDomain) {
        if (!partnerManagerRepository.existsByIdAndPartnerCompany_Domain(partnerManagerId, partnerDomain)) {
            throw new DomainLogicException(PARTNER_MANAGER_NOT_MATCH_WITH_PARTNER_DOMAIN);
        }
    }
}
