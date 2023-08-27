package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Customer.CustomerRepository;
import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.dto.response.customer.CustomerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PartnerCompanyService partnerCompanyService;

    @Transactional
    public String create(String partnerDomain, CustomerCreateRequest request) {
        validateUniquePartnerCustomerId(partnerDomain, request.getPartnerCustomId());
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        PartnerCompany partnerCompany = partnerCompanyService.findByPartnerDomain(partnerDomain);

        return customerRepository.save(request.toEntity(partnerCompany)).getPartnerCustomId();
    }

    @Transactional
    public Customer retreiveCustomer(String partnerDomain, CustomerCreateRequest customerCreateRequest) {
        if (existsByPartnerDomainAndPartnerCustomId(partnerDomain, customerCreateRequest.getPartnerCustomId())) {
            return findByPartnerDomainAndPartnerCustomId(partnerDomain, customerCreateRequest.getPartnerCustomId());
        }

        create(partnerDomain, customerCreateRequest);
        return findByPartnerDomainAndPartnerCustomId(partnerDomain, customerCreateRequest.getPartnerCustomId());
    }

    public boolean existsByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return customerRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    @Transactional
    public void updateByCustomerId(String partnerDomain, String partnerCustomId, CustomerUpdateRequest request) {
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        Customer customer = findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
        customer.update(request);
    }

    private void validateUniquePartnerCustomerId(String partnerDomain, String partnerCustomId) {
        if (customerRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)) {
            throw new DomainLogicException(CUSTOMER_DUPLICATED_PARTNER_ID);
        }
    }

    private void validateUniquePhoneNumber(String phoneNumber) {
        if (customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DomainLogicException(CUSTOMER_NOT_UNIQUE_PHONE_NUMBER);
        }
    }

    private void validateUniqueKakaoId(String kakaoId) {
        if (customerRepository.existsByKakaoId(kakaoId)) {
            throw new DomainLogicException(CUSTOMER_NOT_UNIQUE_KAKAO_ID);
        }
    }

    public Customer findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return customerRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public CustomerResponse getCustomerResponseByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        final Customer customer = findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);

        return new CustomerResponse(customer);
    }

    @Transactional
    public void deleteByCustomerId(Long customerId) {
        validateExistCustomer(customerId);

        customerRepository.deleteById(customerId);
    }

    public void validateExistCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new DomainLogicException(CUSTOMER_NOT_FOUND);
        }
    }
}
