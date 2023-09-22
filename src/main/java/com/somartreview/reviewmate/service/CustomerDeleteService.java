package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.customer.CustomerRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerDeleteService {

    private final CustomerRepository customerRepository;
    private final ReservationDeleteService reservationDeleteService;


    @Transactional
    public void delete(Long id) {
        validateExistCustomer(id);

        reservationDeleteService.deleteAllByCustomerId(id);
        customerRepository.deleteById(id);
    }

    public void validateExistCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new DomainLogicException(CUSTOMER_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        long customerId = customerRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND))
                .getId();

        reservationDeleteService.deleteAllByCustomerId(customerId);
        customerRepository.deleteByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    public void deleteAllByPartnerDomain(String partnerDomain) {
        customerRepository.deleteAllByPartnerCompany_PartnerDomain(partnerDomain);
    }
}
