package com.somartreview.reviewmate.domain.travelProduct;

import com.somartreview.reviewmate.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
}
