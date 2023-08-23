package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManager;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManagerRepository;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.dto.response.partnerManager.PartnerManagerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerManagerService {

    private final PartnerManagerRepository partnerManagerRepository;
    private final PartnerCompanyService partnerCompanyService;

    @Transactional
    public Long createPartnerManager(PartnerManagerCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findPartnerCompanyByDomain(request.getPartnerCompanyDomain());

        return partnerManagerRepository.save(request.toEntity(partnerCompany)).getId();
    }

    public PartnerManager findPartnerManagerById(Long id) {
        return partnerManagerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_MANAGER_NOT_FOUND));
    }

    public PartnerManagerResponse getPartnerManagerResponseById(Long id) {
        final PartnerManager partnerManager = findPartnerManagerById(id);

        return new PartnerManagerResponse(partnerManager);
    }

    @Transactional
    public void updatePartnerManagerById(Long id, PartnerManagerUpdateRequest request) {
        PartnerManager partnerManager = findPartnerManagerById(id);
        partnerManager.update(request);
    }

    @Transactional
    public void deletePartnerManagerById(Long id) {
        PartnerManager partnerManager = findPartnerManagerById(id);
        partnerManagerRepository.delete(partnerManager);
    }
}
