package com.somartreview.reviewmate.domain.LiveSatisfaction;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Review.Property;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LiveSatisfaction extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "live_satisfaction_id")
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 20)
    private Property satisfiedProperty;

    @Column(nullable = false, length = 20)
    private Property dissatisfiedProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    public LiveSatisfaction(Integer rating, Property satisfiedProperty, Property dissatisfiedProperty, Customer customer, TravelProduct travelProduct) {
        this.rating = rating;
        this.satisfiedProperty = satisfiedProperty;
        this.dissatisfiedProperty = dissatisfiedProperty;

        customer.addLiveSatisfaction(this);
        this.customer = customer;

        travelProduct.addLiveSatisfaction(this);
        this.travelProduct = travelProduct;
    }
}
