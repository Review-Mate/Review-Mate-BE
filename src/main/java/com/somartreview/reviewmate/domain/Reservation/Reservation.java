package com.somartreview.reviewmate.domain.Reservation;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "customer_partner_custom_id", nullable = false),
            @JoinColumn(name = "customer_partner_domain", nullable = false)
    })
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "travel_product_partner_custom_id", nullable = false),
            @JoinColumn(name = "travel_product_partner_domain", nullable = false)
    })
    private TravelProduct travelProduct;

    public Reservation(Customer customer, TravelProduct travelProduct) {
        this.customer = customer;
        this.travelProduct = travelProduct;
    }
}
