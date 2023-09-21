package com.somartreview.reviewmate.domain.product;


import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import com.somartreview.reviewmate.dto.product.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.domain.product.SingleTravelProductCategory.*;
import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleTravelProductTest {

    private SingleTravelProduct singleTravelProduct;

    @BeforeEach
    void setup() {
        singleTravelProduct = SingleTravelProduct.builder()
                .partnerCustomId("PRODUCT-0001")
                .thumbnailUrl("www.thumbnail.com")
                .name("신라더스테이")
                .partnerCompany(new PartnerCompany())
                .partnerSeller(new PartnerSeller())
                .singleTravelProductCategory(ACCOMMODATION)
                .build();
    }
    
    
    @Test
    void 단일_여행상품의_정보를_수정한다() {
        // given
        SingleTravelProductUpdateRequest singleTravelProductUpdateRequest = new SingleTravelProductUpdateRequest("김해더스테이", RESTAURANT);
        String updatedThumbnailUrl = "www.chanho.com";

        // when
        singleTravelProduct.update(singleTravelProductUpdateRequest, updatedThumbnailUrl);

        // then
        assertThat(singleTravelProduct)
                .extracting("thumbnailUrl", "name", "singleTravelProductCategory")
                .containsExactly("www.chanho.com", "김해더스테이", RESTAURANT);
    }

    @Test
    void 단일_여행상품의_커스텀_Id가_공백이면_안된다() {
        // given
        String partnerCustomId = " ";
        PartnerCompany mockPartnerCompany = new PartnerCompany();
        PartnerSeller mockPartnerSeller = new PartnerSeller();

        // when & then
        assertThatThrownBy(() -> new SingleTravelProduct(partnerCustomId, "www.thumbnail.com", "신라더스테이", mockPartnerCompany, mockPartnerSeller, ACCOMMODATION))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 단일_여행상품의_커스텀_Id가_50자_보다_길면_안된다() {
        // given
        String partnerCustomId = "a".repeat(51);
        PartnerCompany mockPartnerCompany = new PartnerCompany();
        PartnerSeller mockPartnerSeller = new PartnerSeller();

        // when & then
        assertThatThrownBy(() -> new SingleTravelProduct(partnerCustomId, "www.thumbnail.com", "신라더스테이", mockPartnerCompany, mockPartnerSeller, ACCOMMODATION))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 단일_여행상품의_썸네일url이_공백이면_안된다() {
        // given
        String thumbnailUrl = " ";
        PartnerCompany mockPartnerCompany = new PartnerCompany();
        PartnerSeller mockPartnerSeller = new PartnerSeller();

        // when & then
        assertThatThrownBy(() -> new SingleTravelProduct("PRODUCT_0001", thumbnailUrl, "신라더스테이", mockPartnerCompany, mockPartnerSeller, ACCOMMODATION))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR.getMessage());
    }

    @Test
    void 단일_여행상품의_썸네일url이_1024자_보다_길면_안된다() {
        // given
        String thumbnailUrl = "a".repeat(1025);
        PartnerCompany mockPartnerCompany = new PartnerCompany();
        PartnerSeller mockPartnerSeller = new PartnerSeller();

        // when & then
        assertThatThrownBy(() -> new SingleTravelProduct("PRODUCT_0001", thumbnailUrl, "신라더스테이", mockPartnerCompany, mockPartnerSeller, ACCOMMODATION))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_THUMBNAIL_URL_ERROR.getMessage());
    }

    @Test
    void 단일_여행상품의_이름이_공백이면_안된다() {
        // given
        String name = " ";
        PartnerCompany mockPartnerCompany = new PartnerCompany();
        PartnerSeller mockPartnerSeller = new PartnerSeller();

        // when & then
        assertThatThrownBy(() -> new SingleTravelProduct("PRODUCT_0001", "www.thumbnail.com", name, mockPartnerCompany, mockPartnerSeller, ACCOMMODATION))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_NAME_ERROR.getMessage());
    }

    @Test
    void 단일_여행상품의_이름이_255자_보다_길면_안된다() {
        // given
        String name = "a".repeat(256);
        PartnerCompany mockPartnerCompany = new PartnerCompany();
        PartnerSeller mockPartnerSeller = new PartnerSeller();

        // when & then
        assertThatThrownBy(() -> new SingleTravelProduct("PRODUCT_0001", "www.thumbnail.com", name, mockPartnerCompany, mockPartnerSeller, ACCOMMODATION))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(TRAVEL_PRODUCT_NAME_ERROR.getMessage());
    }

    @Test
    void 단일_여행상품에_리뷰가_추가된다() {
        // given & when
        singleTravelProduct.updateReviewData(5);

        // then
        assertThat(singleTravelProduct)
                .extracting("reviewCount", "rating")
                .containsExactly(1, 5.0f);
    }

    @Test
    void 단일_여행상품에_리뷰가_삭제된다() {
        // given & when
        singleTravelProduct.updateReviewData(5);
        singleTravelProduct.updateReviewData(5);
        singleTravelProduct.removeReview(5);

        // then
        assertThat(singleTravelProduct)
                .extracting("reviewCount", "rating")
                .containsExactly(1, 5.0f);
    }

    @Test
    void 단일_여행상품에_리뷰가_삭제될때_리뷰갯수가_0개여도_dividedByZero_예외가_발생하지_않는다() {
        // given & when
        singleTravelProduct.updateReviewData(5);
        singleTravelProduct.removeReview(5);

        // then
        assertThat(singleTravelProduct)
                .extracting("reviewCount", "rating")
                .containsExactly(0, 0.0f);
    }
}