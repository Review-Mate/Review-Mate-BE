package com.somartreview.reviewmate.domain.travelProduct;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor
public abstract class TravelProduct extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "travel_product_id")
    private Long id;

    @Column(nullable = false)
    private String clientSideProductId;

    private String thumbnailUrl;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private Float rating = 0.0f;

    @OneToMany(mappedBy = "travelProductId")
    private List<Reservation> reservations = new ArrayList<>();

    public TravelProduct(String clientSideProductId, String thumbnailUrl, String name, Float rating) {
        this.clientSideProductId = clientSideProductId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.rating = rating;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}
