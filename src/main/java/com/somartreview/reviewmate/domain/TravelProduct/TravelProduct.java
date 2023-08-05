package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.LiveFeedback.LiveFeedback;
import com.somartreview.reviewmate.domain.LiveSatisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Review.Review;
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

    @OneToMany(mappedBy = "travelProductId")
    private List<LiveSatisfaction> liveSatisfactions = new ArrayList<>();

    @OneToMany(mappedBy = "travelProductId")
    private List<LiveFeedback> liveFeedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "travelProductId")
    private List<Review> reviews = new ArrayList<>();

    public TravelProduct(String clientSideProductId, String thumbnailUrl, String name, Float rating) {
        this.clientSideProductId = clientSideProductId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.rating = rating;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void addLiveSatisfaction(LiveSatisfaction liveSatisfaction) {
        this.liveSatisfactions.add(liveSatisfaction);
    }

    public void addLiveFeedback(LiveFeedback liveFeedback) {
        this.liveFeedbacks.add(liveFeedback);
    }

    public void addReview(Review review) {
        int reviewCount = this.reviews.size();
        rating = (rating * reviewCount + review.getRating()) / (reviewCount + 1);

        this.reviews.add(review);
    }
}
