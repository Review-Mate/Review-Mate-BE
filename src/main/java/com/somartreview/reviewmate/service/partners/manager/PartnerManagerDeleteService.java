package com.somartreview.reviewmate.service.partners.manager;

import com.somartreview.reviewmate.domain.partner.manager.PartnerManagerRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.PARTNER_MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PartnerManagerDeleteService {

    private final PartnerManagerRepository partnerManagerRepository;


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

    public void deleteAllByPartnerDomain(String partnerDomain) {
        partnerManagerRepository.deleteAllByPartnerCompany_PartnerDomain(partnerDomain);
    }
}
