package com.somartreview.reviewmate.domain.reservation;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.travelProduct.TravelProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id")
    private TravelProduct travelProduct;

    public Reservation(Customer customer, TravelProduct travelProduct) {
        customer.addReservation(this);
        this.customer = customer;

        travelProduct.addReservation(this);
        this.travelProduct = travelProduct;
    }
}
