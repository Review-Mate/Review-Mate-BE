package com.somartreview.reviewmate.service.customers;

import com.somartreview.reviewmate.domain.customer.CustomerRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.reservation.ReservationDeleteService;
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

        // 고객 정보 비활성화로 대체
        customerRepository.deleteById(id);
    }

    public void validateExistCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new DomainLogicException(CUSTOMER_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        customerRepository.deleteByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    public void deleteAllByPartnerDomain(String partnerDomain) {
        customerRepository.deleteAllByPartnerCompany_PartnerDomain(partnerDomain);
    }
}
