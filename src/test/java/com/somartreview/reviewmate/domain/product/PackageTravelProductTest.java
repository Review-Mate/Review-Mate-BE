package com.somartreview.reviewmate.domain.product;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PackageTravelProductTest {

    private PackageTravelProduct packageTravelProduct;

    @BeforeEach
    void setup() {
        packageTravelProduct = PackageTravelProduct.builder()
                .partnerCustomId("PRODUCT-0001")
                .thumbnailUrl("www.thumbnail.com")
                .name("신라더스테이")
                .partnerCompany(new PartnerCompany())
                .partnerSeller(new PartnerSeller())
                .build();
    }

    @Test
    void 패키지_여행상품에_여행코스를_추가한다() {
        // given
        TourCourse tourCourse = new TourCourse();

        // when
        packageTravelProduct.addTourCourse(tourCourse);

        // then
        assertThat(packageTravelProduct.getTourCourses()).hasSize(1);
    }
}