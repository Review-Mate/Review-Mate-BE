package com.somartreview.reviewmate.domain.live.feedback;


import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LiveFeedbackTest {

    private LiveFeedback liveFeedback;

    @BeforeEach
    void setup() {
        liveFeedback = LiveFeedback.builder()
                .customerMessage("리포트 메시지")
                .customerMediaUrl("www.media.com")
                .sellerMessage("응답 메시지")
                .build();
    }


    @Test
    void 라이브피드백의_고객메시지가_공백이면_안된다() {
        // given
        String customerMessage = " ";

        // when & then
        assertThatThrownBy(() -> new LiveFeedback(customerMessage, "www.media.com", "응답 메시지", new Reservation()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_MESSAGE_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_고객메시지가_255자_보다_길면_안된다() {
        // given
        String customerMessage = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new LiveFeedback(customerMessage, "www.media.com", "응답 메시지", new Reservation()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_MESSAGE_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_고객미디어url이_공백이면_안된다() {
        // given
        String customerMediaUrl = " ";

        // when & then
        assertThatThrownBy(() -> new LiveFeedback("리포트 메시지", customerMediaUrl, "응답 메시지", new Reservation()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_MEDIA_URL_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_고객미디어url이_1025자_보다_길면_안된다() {
        // given
        String customerMediaUrl = "a".repeat(1025);

        // when & then
        assertThatThrownBy(() -> new LiveFeedback("리포트 메시지", customerMediaUrl, "응답 메시지", new Reservation()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_MEDIA_URL_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_판매자메시지가_공백이면_안된다() {
        // given
        String sellerMessage = " ";

        // when & then
        assertThatThrownBy(() -> new LiveFeedback("리포트 메시지", "www.media.com", sellerMessage, new Reservation()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_MESSAGE_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_판매자메시지가_255자_보다_길면_안된다() {
        // given
        String sellerMessage = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new LiveFeedback("리포트 메시지", "www.media.com", sellerMessage, new Reservation()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_MESSAGE_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_처리가_완료된다() {
        // when
        liveFeedback.handle();

        // then
        assertThat(liveFeedback.getIsHandled()).isTrue();
    }

    @Test
    void 라이브피드백의_신고가_완료된다() {
        // when
        liveFeedback.handle();
        liveFeedback.report();

        // then
        assertThat(liveFeedback.getIsReported()).isTrue();
    }

    @Test
    void 라이브피드백이_처리되기_전에_신고되면_안된다() {
        // when & then
        assertThatThrownBy(() -> liveFeedback.report())
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_NOT_HANDLED_YET.getMessage());
    }

    @Test
    void 라이브피드백이_이미_해결됐으면_신고되면_안된다() {
        // given
        liveFeedback.handle();
        liveFeedback.solve();

        // when & then
        assertThatThrownBy(() -> liveFeedback.report())
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_ALREADY_SOLVED_ERROR.getMessage());
    }

    @Test
    void 라이브피드백의_해결이_완료된다() {
        // when
        liveFeedback.handle();
        liveFeedback.solve();

        // then
        assertThat(liveFeedback.getIsSolved()).isTrue();
    }

    @Test
    void 라이브피드백이_처리되기_전에_해결되면_안된다() {
        // when & then
        assertThatThrownBy(() -> liveFeedback.solve())
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_NOT_HANDLED_YET.getMessage());
    }

    @Test
    void 라이브피드백이_이미_신고됐으면_해결되면_안된다() {
        // given
        liveFeedback.handle();
        liveFeedback.report();

        // when & then
        assertThatThrownBy(() -> liveFeedback.solve())
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(ErrorCode.LIVE_FEEDBACK_ALREADY_REPORTED_ERROR.getMessage());
    }
}