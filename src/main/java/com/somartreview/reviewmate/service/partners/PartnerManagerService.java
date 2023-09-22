package com.somartreview.reviewmate.service.partners;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.manager.PartnerManager;
import com.somartreview.reviewmate.domain.partner.manager.PartnerManagerRepository;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerResponse;
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
    public Long create(PartnerManagerCreateRequest request) {
        final PartnerCompany partnerCompany = partnerCompanyService.findByPartnerDomain(request.getPartnerCompanyDomain());

        return partnerManagerRepository.save(request.toEntity(partnerCompany)).getId();
    }

    public PartnerManager findById(Long id) {
        return partnerManagerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_MANAGER_NOT_FOUND));
    }

    public PartnerManagerResponse getPartnerManagerResponseById(Long id) {
        final PartnerManager partnerManager = findById(id);

        return new PartnerManagerResponse(partnerManager);
    }

    @Transactional
    public void update(Long id, PartnerManagerUpdateRequest request) {
        PartnerManager partnerManager = findById(id);
        partnerManager.update(request);
    }

    @Transactional
    public void delete(Long id) {
        validateExistPartnerManagerById(id);

        partnerManagerRepository.deleteById(id);
    }

    private void validateExistPartnerManagerById(Long id) {
        if (!partnerManagerRepository.existsById(id)) {
            throw new DomainLogicException(PARTNER_MANAGER_NOT_FOUND);
        }
    }
}
