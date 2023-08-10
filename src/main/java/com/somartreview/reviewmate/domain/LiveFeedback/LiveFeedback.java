package com.somartreview.reviewmate.domain.LiveFeedback;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LiveFeedback extends BaseEntity {

    private static final int MAX_MESSAGE_LENGTH = 255;
    private static final int MAX_URL_LENGTH = 1024;

    @Id @GeneratedValue
    @Column(name = "live_feedback_id")
    private Long id;

    @Column(nullable = false)
    private String feedbackMessage;

    @Column(length = 1024)
    private String feedbackMediaUrl;

    private String responseMessage;

    @Column(nullable = false)
    private Boolean isHandled = false;

    @Column(nullable = false)
    private Boolean isReported = false;

    @Column(nullable = false)
    private Boolean isSolved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    public LiveFeedback(String feedbackMessage, String feedbackMediaUrl, String responseMessage, Customer customer, TravelProduct travelProduct) {
        validateFeedbackMessage(feedbackMessage);
        this.feedbackMessage = feedbackMessage;
        validateFeedbackMediaUrl(feedbackMediaUrl);
        this.feedbackMediaUrl = feedbackMediaUrl;
        validateResponseMessage(responseMessage);
        this.responseMessage = responseMessage;
        this.customer = customer;
        this.travelProduct = travelProduct;
    }

    private void validateFeedbackMessage(final String feedbackMessage) {
        if (feedbackMessage.isBlank() || feedbackMessage.length() > MAX_MESSAGE_LENGTH) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_MESSAGE_ERROR);
        }
    }

    private void validateFeedbackMediaUrl(final String feedbackMediaUrl) {
        if (feedbackMediaUrl.isBlank() || feedbackMediaUrl.length() > MAX_URL_LENGTH) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_MEDIA_URL_ERROR);
        }
    }

    private void validateResponseMessage(final String responseMessage) {
        if (responseMessage == null) {
            return;
        }

        if (responseMessage.isBlank() || responseMessage.length() > MAX_MESSAGE_LENGTH) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_MESSAGE_ERROR);
        }
    }

    public void handleFeedback() {
        if (isReported) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_ALREADY_REPORTED_ERROR);
        }

        isHandled = true;
    }

    public void reportFeedback() {
        if (isHandled) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_ALREADY_HANDLED_ERROR);
        }

        isReported = true;
    }

    public void solveFeedback() {
        if (isSolved) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_ALREADY_SOLVED_ERROR);
        }

        isSolved = true;
    }
}
