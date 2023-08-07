package com.somartreview.reviewmate.domain.Customer;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.LiveFeedback.LiveFeedback;
import com.somartreview.reviewmate.domain.LiveSatisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private Long clientSideUserId;

    @Column(nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    public Customer(Long clientSideUserId, String name, String phoneNumber, String kakaoId) {
        this.clientSideUserId = clientSideUserId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
    }
}
