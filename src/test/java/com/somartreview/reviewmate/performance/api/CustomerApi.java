package com.somartreview.reviewmate.performance.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.Map;

import static com.somartreview.reviewmate.performance.PerformanceTest.*;

public class CustomerApi {

    public static void 커스텀Id로_고객을_조회한다() {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("partnerCustomId", "CUSTOMER_" + PARTNER_CUSTOM_ID_POSTFIX)
                .when().get("api/console/v1/{partnerDomain}/customers/{partnerCustomId}")
                .then()
                .extract();
    }

    public static void 커스텀Id로_고객_정보를_수정한다() {
        Map<String, Object> customerUpdateRequest = Map.of(
                "name", "장현우",
                "phoneNumber", "01012349941",
                "kakaoId", "changhw878_" + PARTNER_CUSTOM_ID_POSTFIX
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("partnerCustomId", "CUSTOMER_" + PARTNER_CUSTOM_ID_POSTFIX)
                .body(customerUpdateRequest)
                .when().put("api/console/v1//{partnerDomain}/customers/{partnerCustomId}")
                .then()
                .extract();
    }

    public static void 커스텀Id로_고객을_삭제한다() {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .pathParam("partnerCustomId", "CUSTOMER_" + PARTNER_CUSTOM_ID_POSTFIX)
                .when().delete("api/console/v1/{partnerDomain}/customers/{partnerCustomId}")
                .then()
                .extract();
    }
}
