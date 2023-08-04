package com.somartreview.reviewmate.domain.travelProduct;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class TravelProduct {

    @Id @GeneratedValue
    @Column(name = "travel_product_id")
    private Long id;

    private String clientSideProductId;

    @Nullable
    private String thumbnailUrl;

    @Column(length = 100)
    private String name;

    private Float rating;
}
