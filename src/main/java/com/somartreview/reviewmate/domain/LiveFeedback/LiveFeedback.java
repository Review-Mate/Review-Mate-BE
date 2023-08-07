package com.somartreview.reviewmate.domain.LiveFeedback;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LiveFeedback extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "live_feedback_id")
    private Long id;

    @Column(nullable = false)
    private String feedbackMessage;

    @Column(length = 1024)
    private String feedbackMediaUrl;

    private String responseMessage;

    @Column(nullable = false)
    private Boolean isHandled;

    @Column(nullable = false)
    private Boolean isReported;

    @Column(nullable = false)
    private Boolean isSolved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    public LiveFeedback(String feedbackMessage, String feedbackMediaUrl, String responseMessage, Boolean isHandled, Boolean isReported, Boolean isSolved, Customer customer, TravelProduct travelProduct) {
        this.feedbackMessage = feedbackMessage;
        this.feedbackMediaUrl = feedbackMediaUrl;
        this.responseMessage = responseMessage;
        this.isHandled = isHandled;
        this.isReported = isReported;
        this.isSolved = isSolved;
        this.customer = customer;
        this.travelProduct = travelProduct;
    }

    public LiveFeedback(String feedbackMessage, String feedbackMediaUrl, String responseMessage, Boolean isHandled, Boolean isReported, Boolean isSolved) {
        this.feedbackMessage = feedbackMessage;
        this.feedbackMediaUrl = feedbackMediaUrl;
        this.responseMessage = responseMessage;
        this.isHandled = isHandled;
        this.isReported = isReported;
        this.isSolved = isSolved;
    }
}
