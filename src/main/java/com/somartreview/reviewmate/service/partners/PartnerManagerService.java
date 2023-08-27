package com.somartreview.reviewmate.service.partners;

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
    public void updateById(Long id, PartnerManagerUpdateRequest request) {
        PartnerManager partnerManager = findById(id);
        partnerManager.update(request);
    }

    @Transactional
    public void deleteById(Long id) {
        validateExistPartnerManagerById(id);

        partnerManagerRepository.deleteById(id);
    }

    public void validateExistPartnerManagerById(Long id) {
        if (!partnerManagerRepository.existsById(id)) {
            throw new DomainLogicException(PARTNER_MANAGER_NOT_FOUND);
        }
    }
}
