package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Customer.CustomerRepository;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.dto.response.customer.CustomerResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Long createCustomer(CustomerCreateRequest request) {
        validateCreatePartnerCustomerId(request.getPartnerCustomerId());

        return customerRepository.save(request.toEntity()).getId();
    }

    private void validateCreatePartnerCustomerId(String partnerCustomerId) {
        if (customerRepository.existsByPartnerCustomerId(partnerCustomerId)) {
            throw new DomainLogicException(CUSTOMER_DUPLICATED_PARTNER_ID);
        }
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public CustomerResponse getCustomerResponseById(Long id) {
        final Customer customer = findCustomerById(id);
        return new CustomerResponse(customer);
    }

    @Transactional
    public void updateCustomerById(Long id, CustomerUpdateRequest request) {
        validateUpdatePartnerCustomerId(request.getPartnerCustomerId());

        customerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND))
                .update(request);
    }

    @Transactional
    public void updateCustomerByPartnerCustomerId(String partnerCustomerId, CustomerUpdateRequest request) {
        validateUpdatePartnerCustomerId(request.getPartnerCustomerId());

        customerRepository.findByPartnerCustomerId(partnerCustomerId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND))
                .update(request);
    }

    private void validateUpdatePartnerCustomerId(String partnerCustomerId) {
        if (customerRepository.countByPartnerCustomerId(partnerCustomerId) >= 2) {
            throw new DomainLogicException(CUSTOMER_DUPLICATED_PARTNER_ID);
        }
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
    }

    @Transactional
    public void deleteCustomerByPartnerCustomerId(String partnerCustomerId) {
        Customer customer = customerRepository.findByPartnerCustomerId(partnerCustomerId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
    }
}
