package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.CustomerRepository;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Long createCustomer(CustomerCreateRequest request) {
        return customerRepository.save(request.toEntity()).getId();
    }
}
