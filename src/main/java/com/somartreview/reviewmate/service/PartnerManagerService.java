package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.PartnerManager.PartnerManager;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManagerRepository;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.request.partnerManager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerManagerService {

    private final PartnerManagerRepository partnerManagerRepository;

    @Transactional
    public Long createPartnerManager(PartnerManagerCreateRequest request) {
        return partnerManagerRepository.save(request.toEntity()).getId();
    }

    public PartnerManager findPartnerManagerById(Long id) {
        return partnerManagerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(PARTNER_MANAGER_NOT_FOUND));
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
