package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.CustomerRepository;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Long createCustomer(CustomerCreateRequest request) {
        if (customerRepository.existsByClientSideId(request.getClientSideId())) {
            throw new DomainLogicException(CUSTOMER_DUPLICATED_CLIENT_SIDE_ID);
        }

        return customerRepository.save(request.toEntity()).getId();
    }
}
