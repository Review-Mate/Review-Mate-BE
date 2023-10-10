package com.somartreview.reviewmate.performance.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

import static com.somartreview.reviewmate.performance.PerformanceTest.PARTNER_CUSTOM_ID_POSTFIX;
import static com.somartreview.reviewmate.performance.PerformanceTest.PARTNER_DOMAIN;

public class SingleTravelProductAPIRequester {

    public static ExtractableResponse<Response> 파트너ID로_단일_여행상품_목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("partnerCustomId", "PRODUCT_" + PARTNER_CUSTOM_ID_POSTFIX)
                .when().get("api/console/v1/{partnerDomain}/products/travel/single/{partnerCustomId}")
                .then().extract();
    }

    public static ExtractableResponse<Response> 카테고리별_단일_여행상품_목록을_조회한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .queryParam("singleTravelProductCategory", "ACCOMMODATION")
                .when().get("api/console/v1/{partnerDomain}/products/travel/single")
                .then().extract();
    }

    public static ExtractableResponse<Response> 단일_여행상품_정보를_수정한다() {
        Map<String, Object> singleTravelProductUpdateRequest = Map.of(
                "name", "순대국밥집",
                "singleTravelProductCategory", "RESTAURANT"
        );

        return RestAssured.given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("singleTravelProductUpdateRequest", singleTravelProductUpdateRequest, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("partnerCustomId", "PRODUCT_" + PARTNER_CUSTOM_ID_POSTFIX)
                .when().patch("api/console/v1/{partnerDomain}/products/travel/single/{partnerCustomId}")
                .then().extract();
    }

    public static ExtractableResponse<Response> 파트너ID로_단일_여행상품을_삭제한다(String partnerCustomId) {
        return RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("partnerCustomId", partnerCustomId)
                .when().delete("api/console/v1/{partnerDomain}/products/travel/single/{partnerCustomId}")
                .then().extract();
    }
}