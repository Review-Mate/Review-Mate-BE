package com.somartreview.reviewmate.service.customers;

import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.customer.CustomerRepository;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.dto.customer.CustomerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.partners.company.PartnerCompanyService;
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
    public Customer create(String partnerDomain, CustomerCreateRequest request) {
        validateUniquePartnerCustomerId(partnerDomain, request.getPartnerCustomId());
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        final PartnerCompany partnerCompany = partnerCompanyService.findByPartnerDomain(partnerDomain);

        return customerRepository.save(request.toEntity(partnerCompany));
    }

    @Transactional
    public Customer retreiveCustomer(String partnerDomain, CustomerCreateRequest customerCreateRequest) {
        if (existsByPartnerDomainAndPartnerCustomId(partnerDomain, customerCreateRequest.getPartnerCustomId())) {
            return findByPartnerDomainAndPartnerCustomId(partnerDomain, customerCreateRequest.getPartnerCustomId());
        }

        return create(partnerDomain, customerCreateRequest);
    }

    private boolean existsByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return customerRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    @Transactional
    public void update(String partnerDomain, String partnerCustomId, CustomerUpdateRequest request) {
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        Customer customer = findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
        customer.update(request);
    }

    @Transactional
    public void update(Long id, CustomerUpdateRequest request) {
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        Customer customer = findById(id);
        customer.update(request);
    }

    private void validateUniquePartnerCustomerId(String partnerDomain, String partnerCustomId) {
        if (customerRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)) {
            throw new DomainLogicException(CUSTOMER_NOT_UNIQUE_PARTNER_CUSTOM_ID);
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

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public Customer findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return customerRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public CustomerResponse getCustomerResponseById(Long id) {
        final Customer customer = findById(id);

        return new CustomerResponse(customer);
    }

    public CustomerResponse getCustomerResponseByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        final Customer customer = findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);

        return new CustomerResponse(customer);
    }
}
