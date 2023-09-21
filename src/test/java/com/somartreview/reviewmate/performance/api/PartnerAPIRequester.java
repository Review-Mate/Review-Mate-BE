package com.somartreview.reviewmate.performance.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.somartreview.reviewmate.performance.PerformanceTest.*;

public class PartnerAPIRequester {

    public static ExtractableResponse<Response> 파트너사를_삭제한다() {
        return RestAssured.given()
                .pathParam("partnerDomain", DELETING_PARTNER_COMPANY_DOMAIN)
                .when().delete("api/console/v1/companies/{partnerDomain}/")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 파트너사_관리자를_삭제한다() {
        return RestAssured.given()
                .pathParam("partnerManagerId", DELETING_PARTNER_MANAGER_ID)
                .when().delete("api/console/v1/managers/{partnerManagerId}/")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 파트너사_판매자를_삭제한다() {
        return RestAssured.given()
                .pathParam("partnerSellerId", DELETING_PARTNER_SELLER_ID)
                .when().delete("api/console/v1/sellers/{partnerSellerId}/")
                .then()
                .extract();
    }
}
