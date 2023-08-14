package com.somartreview.reviewmate.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByClientSideId(String clientSideUserId);

    Optional<Customer> findByClientSideId(String clientSideUserId);

    void deleteByClientSideId(String clientSideUserId);
}
