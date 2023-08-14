package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.CustomerRepository;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerUpdateRequest;
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
        if (customerRepository.existsByClientSideId(request.getClientSideId())) {
            throw new DomainLogicException(CUSTOMER_DUPLICATED_CLIENT_SIDE_ID);
        }

        return customerRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public void updateCustomerByClientSideId(String clientSideId, CustomerUpdateRequest request) {
        customerRepository.findByClientSideId(clientSideId)
                .orElseThrow(() -> new DomainLogicException(CUSTOMER_NOT_FOUND))
                .update(request);
    }
}
