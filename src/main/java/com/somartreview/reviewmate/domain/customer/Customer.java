package com.somartreview.reviewmate.domain.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Customer {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private Long clientSideUserId;

    @Column(length = 100)
    private String username;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String kakaoId;

}
