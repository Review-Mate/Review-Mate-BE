package com.somartreview.reviewmate.domain.Customer;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.LiveSatisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
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

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String kakaoId;

    @OneToMany(mappedBy = "customerId")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "customerId")
    private List<LiveSatisfaction> liveSatisfactions = new ArrayList<>();

    public Customer(Long clientSideUserId, String username, String phoneNumber, String kakaoId) {
        this.clientSideUserId = clientSideUserId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void addLiveSatisfaction(LiveSatisfaction liveSatisfaction) {
        this.liveSatisfactions.add(liveSatisfaction);
    }
}
