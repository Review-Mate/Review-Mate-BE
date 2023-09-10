package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.customer.CustomerRepository;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.dto.customer.CustomerResponse;
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
    public Long create(String partnerDomain, CustomerCreateRequest request) {
        validateUniquePartnerCustomerId(partnerDomain, request.getPartnerCustomId());
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        PartnerCompany partnerCompany = partnerCompanyService.findByPartnerDomain(partnerDomain);

        return customerRepository.save(request.toEntity(partnerCompany)).getId();
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
    public void updateByPartnerCustomId(String partnerDomain, String partnerCustomId, CustomerUpdateRequest request) {
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        Customer customer = findByPartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
        customer.update(request);
    }

    @Transactional
    public void updateByCustomerId(Long customerId, CustomerUpdateRequest request) {
        validateUniquePhoneNumber(request.getPhoneNumber());
        validateUniqueKakaoId(request.getKakaoId());

        Customer customer = findByCustomerId(customerId);
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

    public Customer findByCustomerId(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public Customer findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return customerRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public CustomerResponse getCustomerResponseByCustomerId(Long customerId) {
        final Customer customer = findByCustomerId(customerId);

        return new CustomerResponse(customer);
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

    @Transactional
    public void deleteByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        validateExistCustomer(partnerDomain, partnerCustomId);

        customerRepository.deleteByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId);
    }

    public void validateExistCustomer(String partnerDomain, String partnerCustomId) {
        if (!customerRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)) {
            throw new DomainLogicException(CUSTOMER_NOT_FOUND);
        }
    }
}
