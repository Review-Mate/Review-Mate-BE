package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Customer.CustomerId;
import com.somartreview.reviewmate.domain.Customer.CustomerRepository;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerIdDto;
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
    public Customer create(String partnerDomain, CustomerCreateRequest request) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        return customerRepository.save(request.toEntity(partnerDomain));
    }

    public void validateExistCustomer(CustomerIdDto customerIdDto) {
        if (!customerRepository.existsByCustomerId(customerIdDto.toEntity()))
            throw new DomainLogicException(CUSTOMER_NOT_FOUND);
    }

    public Customer findByCustomerId(CustomerIdDto customerIdDto) {
        return customerRepository.findByCustomerId(customerIdDto.toEntity())
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND));
    }

    public CustomerResponse getCustomerResponseByCustomId(CustomerIdDto customerIdDto) {
        final Customer customer = findByCustomerId(customerIdDto);
        return new CustomerResponse(customer);
    }

    @Transactional
    public void updateByCustomerId(CustomerIdDto customerIdDto, CustomerUpdateRequest request) {
        Customer customer = findByCustomerId(customerIdDto);
        customer.update(request);
    }

    @Transactional
    public void deleteByCustomerId(CustomerIdDto customerIdDto) {
        validateExistCustomer(customerIdDto);

        customerRepository.deleteByCustomerId(customerIdDto.toEntity());
    }
}
