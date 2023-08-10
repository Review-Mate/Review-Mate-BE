package com.somartreview.reviewmate.web;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.somartreview.reviewmate.web.ApiDocumentUtils.getDocumentRequest;
import static com.somartreview.reviewmate.web.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest extends ControllerTest {

    @Test
    void 리뷰를_생성한다() throws Exception {

        // when
        ResultActions response = mockMvc.perform(
                        multipart("/api/v1/review/")
                                .file("reviewImages[]", testImage1.getBytes())
//                                .header("Authorization", BEARER_TOKEN_TYPE + SAMPLE_TOKEN)
                                .param("rating", "5")
                                .param("title", "리뷰 제목")
                                .param("content", "리뷰 내용")
                                .param("travelProductId", "1")
                                .param("customerId", "1")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/review/1"));


        // then
        response.andDo(
                MockMvcRestDocumentationWrapper.document("review-createReview",
                        requestHeaders(
//                                     headerWithName(HttpHeaders.AUTHORIZATION).description("API KEY"),
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.MULTIPART_FORM_DATA_VALUE)
                        ), requestParts(
                                partWithName("reviewImages[]").description("리뷰 이미지 파일")
                        ), requestParameters(
                                RequestDocumentation.parameterWithName("rating").description("별점"),
                                RequestDocumentation.parameterWithName("title").description("리뷰 제목"),
                                RequestDocumentation.parameterWithName("content").description("리뷰 내용"),
                                RequestDocumentation.parameterWithName("travelProductId").description("여행 상품 ID"),
                                RequestDocumentation.parameterWithName("customerId").description("고객(업로더) ID")
                        ))
////                .andDo(
////                MockMvcRestDocumentationWrapper.document("review-createReview",
////                        getDocumentRequest(),
////                        getDocumentResponse(),
////                        resource(ResourceSnippetParameters.builder()
////                                .tag("Review controller")
////                                .description("리뷰")
////                                .requestHeaders(
//////                                     headerWithName(HttpHeaders.AUTHORIZATION).description("API KEY"),
////                                        headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.MULTIPART_FORM_DATA_VALUE)
////                                ).responseHeaders(
////                                        headerWithName(HttpHeaders.LOCATION).description("생성된 리뷰의 URI")
////                                )
////                                .requestParameters(
////                                        ResourceDocumentation.parameterWithName("rating").description("별점"),
////                                        ResourceDocumentation.parameterWithName("title").description("리뷰 제목"),
////                                        ResourceDocumentation.parameterWithName("content").description("리뷰 내용"),
////                                        ResourceDocumentation.parameterWithName("travelProductId").description("여행 상품 ID"),
////                                        ResourceDocumentation.parameterWithName("customerId").description("고객(업로더) ID"),
////                                        ResourceDocumentation.parameterWithName("reviewImages[]").description("리뷰 이미지 파일")
////                                ).requestSchema(
////                                        Schema.schema("ReviewCreateRequest")
////                                ).build())
//                )
        );
    }
}