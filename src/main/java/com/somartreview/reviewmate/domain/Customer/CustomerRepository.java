package com.somartreview.reviewmate.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByCustomerId(CustomerId customerId);

    Optional<Customer> findByCustomerId(CustomerId customerId);

    void deleteByCustomerId(CustomerId customerId);
}
