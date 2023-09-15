package com.somartreview.reviewmate.domain.live.feedback;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.exception.DomainLogicException;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Entity
@Getter
@NoArgsConstructor
public class LiveFeedback extends BaseEntity {

    private static final int MAX_MESSAGE_LENGTH = 255;
    private static final int MAX_URL_LENGTH = 1024;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "live_feedback_id")
    private Long id;

    @Column(nullable = false)
    private String customerMessage;

    @Column(length = 1024)
    private String customerMediaUrl;

    private String sellerMessage;

    @Column(nullable = false)
    private Boolean isHandled = false;

    @Column(nullable = false)
    private Boolean isReported = false;

    @Column(nullable = false)
    private Boolean isSolved = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public LiveFeedback(String customerMessage, String customerMediaUrl, String sellerMessage, final Reservation reservation) {
        validateCustomerMessage(customerMessage);
        this.customerMessage = customerMessage;
        validateCustomerMediaUrl(customerMediaUrl);
        this.customerMediaUrl = customerMediaUrl;
        validateSellerMessage(sellerMessage);
        this.sellerMessage = sellerMessage;
        this.reservation = reservation;
    }

    private void validateCustomerMessage(final String customerMessage) {
        if (customerMessage.isBlank() || customerMessage.length() > MAX_MESSAGE_LENGTH) {
            throw new DomainLogicException(LIVE_FEEDBACK_MESSAGE_ERROR);
        }
    }

    private void validateCustomerMediaUrl(final String customerMediaUrl) {
        if (customerMediaUrl == null) {
            return;
        }

        if (customerMediaUrl.isBlank() || customerMediaUrl.length() > MAX_URL_LENGTH) {
            throw new DomainLogicException(LIVE_FEEDBACK_MEDIA_URL_ERROR);
        }
    }

    private void validateSellerMessage(final String sellerMessage) {
        if (sellerMessage == null) {
            return;
        }

        if (sellerMessage.isBlank() || sellerMessage.length() > MAX_MESSAGE_LENGTH) {
            throw new DomainLogicException(LIVE_FEEDBACK_MESSAGE_ERROR);
        }
    }

    public void handle() {
        isHandled = true;
    }

    public void report() {
        if (isHandled.equals(false)) {
            throw new DomainLogicException(LIVE_FEEDBACK_NOT_HANDLED_YET);
        }

        if (isSolved.equals(true)) {
            throw new DomainLogicException(LIVE_FEEDBACK_ALREADY_SOLVED_ERROR);
        }

        isReported = true;
    }

    public void solve() {
        if (isHandled.equals(false)) {
            throw new DomainLogicException(LIVE_FEEDBACK_NOT_HANDLED_YET);
        }

        if (isReported.equals(true)) {
            throw new DomainLogicException(LIVE_FEEDBACK_ALREADY_REPORTED_ERROR);
        }

        isSolved = true;
    }
}
