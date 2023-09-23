package com.somartreview.reviewmate.performance.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

import static com.somartreview.reviewmate.performance.PerformanceTest.*;

public class ReviewAPIRequester {

    public static ExtractableResponse<Response> 리뷰를_생성한다() {
        Map<String, Object> reviewCreateRequest = Map.of(
                "rating", 3,
                "title", "리뷰 제목",
                "content", "리뷰 내용"
        );

        return RestAssured.given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("reviewCreateRequest", reviewCreateRequest, MediaType.APPLICATION_JSON_VALUE)
//                .multiPart("reviewImageFiles", new File("src/test/resources/images/logo_b.jpg"), MediaType.MULTIPART_FORM_DATA_VALUE)
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("reservationPartnerCustomId", "RESERVATION_" + PARTNER_CUSTOM_ID_POSTFIX)
                .when().post("api/widget/v1/{partnerDomain}/reservations/{reservationPartnerCustomId}/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰Id로_리뷰를_조회한다() {
        return RestAssured.given()
                .pathParam("reviewId", SAFE_COMMON_ID)
                .when().get("/api/widget/v1/reviews/{reviewId}")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품의_파트너Id로_리뷰목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("travelProductPartnerCustomId", SAFE_COMMON_ID)
                .when().get("/api/widget/v1/{partnerDomain}/products/{travelProductPartnerCustomId}/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품의_파트너Id로_속성_필터링으로_리뷰목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("travelProductPartnerCustomId", SAFE_COMMON_ID)
                .param("property", "CLEANNESS")
                .when().get("/api/widget/v1/{partnerDomain}/products/{travelProductPartnerCustomId}/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품의_파트너Id로_키워드_필터링으로_리뷰목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("travelProductPartnerCustomId", SAFE_COMMON_ID)
                .param("property", "CLEANNESS")
                .param("keyword", "냄새")
                .when().get("/api/widget/v1/{partnerDomain}/products/{travelProductPartnerCustomId}/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품의_파트너Id로_별점순_정렬로_리뷰목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("travelProductPartnerCustomId", SAFE_COMMON_ID)
                .param("orderCriteria", "RATING_DESC")
                .when().get("/api/widget/v1/{partnerDomain}/products/{travelProductPartnerCustomId}/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품의_파트너Id로_긍정적인순_정렬로_리뷰목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("travelProductPartnerCustomId", SAFE_COMMON_ID)
                .param("orderCriteria", "POSITIVE_DESC")
                .when().get("/api/widget/v1/{partnerDomain}/products/{travelProductPartnerCustomId}/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 단일_여행상품의_리뷰_통계를_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("singleTravelProductPartnerCustomId", SAGE_PRODUCT_PARTNER_CUSTOM_ID)
                .when().get("/api/widget/v1/{partnerDomain}/products/{singleTravelProductPartnerCustomId}/statistic/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 단일_여행상품의_리뷰태그_통계를_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("singleTravelProductPartnerCustomId", SAGE_PRODUCT_PARTNER_CUSTOM_ID)
                .when().get("/api/widget/v1/{partnerDomain}/products/{singleTravelProductPartnerCustomId}/statistic/tags")
                .then()
                .extract();
    }
}
