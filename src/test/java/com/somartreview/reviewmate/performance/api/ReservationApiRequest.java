package com.somartreview.reviewmate.performance.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.io.File;
import java.util.Map;

public class ReservationApiRequest {

    public static ExtractableResponse<Response> 예약을_생성한다() {
        Map<String, Object> customerCreateRequest = Map.of(
                "partnerCustomId", "CUSTOMER_0915_1",
                "name", "권순찬",
                "phoneNumber", "01009159941",
                "kakaoId", "sckwon770_0915_1"
        );
        Map<String, Object> singleTravelProductCreateRequest = Map.of(
                "partnerCustomId", "PRODUCT_0915_1",
                "name", "신라더스테이 호텔",
                "singleTravelProductCategory", "ACCOMMODATION",
                "partnerSellerId", 1L
        );
        Map<String, Object> singleTravelReservationCreateRequest = Map.of(
                "partnerCustomId", "RESERVATION_0915_1",
                "startDateTime", "2023-10-18T13:00:00",
                "endDateTime", "2023-10-19T12:00:00",
                "customerCreateRequest", customerCreateRequest,
                "singleTravelProductCreateRequest", singleTravelProductCreateRequest
        );

        return RestAssured.given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .pathParam("partnerDomain", "company0.com")
                .multiPart("singleTravelReservationCreateRequest", singleTravelReservationCreateRequest, MediaType.APPLICATION_JSON_VALUE)
                .multiPart("singleTravelProductThumbnail", new File("src/test/resources/images/logo_b.jpg"), MediaType.MULTIPART_FORM_DATA_VALUE)
                .when().post("api/client/v1/{partnerDomain}/products/travel/single/reservations")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 예약Id로_예약을_조회한다() {
        return RestAssured.given()
                .pathParam("reservationId", 3L)
                .when().get("api/console/v1/products/travel/single/reservations/{reservationId}")
                .then()
                .log().all()
                .extract();
    }
}
