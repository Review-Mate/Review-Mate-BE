package com.somartreview.reviewmate.performance.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class PartnerAPIRequester {

    public static ExtractableResponse<Response> 파트너사를_삭제한다(String deletingPartnerCompanyDomain) {
        return RestAssured.given()
                .pathParam("partnerDomain", deletingPartnerCompanyDomain)
                .when().delete("api/console/v1/companies/{partnerDomain}/")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 파트너사_관리자를_삭제한다(long deletingPartnerManagerId) {
        return RestAssured.given()
                .pathParam("partnerManagerId", deletingPartnerManagerId)
                .when().delete("api/console/v1/managers/{partnerManagerId}/")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 파트너사_판매자를_삭제한다(long deletingPartnerSellerId) {
        return RestAssured.given()
                .pathParam("partnerSellerId", deletingPartnerSellerId)
                .when().delete("api/console/v1/sellers/{partnerSellerId}/")
                .then()
                .extract();
    }
}
