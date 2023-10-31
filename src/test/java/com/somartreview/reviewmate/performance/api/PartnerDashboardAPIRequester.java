package com.somartreview.reviewmate.performance.api;

import com.somartreview.reviewmate.domain.partner.console.ConsoleTimeSeriesUnit;
import io.restassured.RestAssured;

import static com.somartreview.reviewmate.performance.PerformanceTest.PARTNER_DOMAIN;

public class PartnerDashboardAPIRequester {

    public static void 시계열_단위별_리뷰_작성률을_조회한다(ConsoleTimeSeriesUnit consoleTimeSeriesUnit) {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .queryParam("consoleTimeSeriesUnit", consoleTimeSeriesUnit)
                .when().get("api/console/v1/{partnerDomain}/dashboard/reviewing-rate")
                .then()
                .extract();
    }

    public static void 카테고리별_리뷰_작성률의_꺽은선_그래프_정보를_조회한다(ConsoleTimeSeriesUnit timeSeriesUnit) {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .queryParam("timeSeriesUnit", timeSeriesUnit)
                .when().get("api/console/v1/{partnerDomain}/dashboard/categories-reviewing-line-chart")
                .then()
                .extract();
    }

    public static void 누적_리뷰_수를_조회한다() {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .when().get("api/console/v1/{partnerDomain}/dashboard/total-review-count")
                .then()
                .extract();
    }

    public static void 달성률의_게이지_그래프_정보를_조회한다() {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .when().get("api/console/v1/{partnerDomain}/dashboard/reviewing-achievement-gauge-chart")
                .then()
                .extract();
    }

    public static void 달성률의_막대_그래프_정보를_조회한다() {
        RestAssured.given()
                .pathParam("partnerDomain", PARTNER_DOMAIN)
                .when().get("api/console/v1/{partnerDomain}/dashboard/reviewing-achievement-bar-chart")
                .then()
                .extract();
    }
}
