package com.somartreview.reviewmate.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;
import com.somartreview.reviewmate.dto.request.review.ReviewUpdateRequest;
import com.somartreview.reviewmate.service.review.ReviewService;
import com.somartreview.reviewmate.web.review.ReviewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    void 리뷰를_생성한다() throws Exception {
        // given
        given(reviewService.create(any(), any())).willReturn(1L);

        MockMultipartFile image1 = new MockMultipartFile(
                "reviewImages[0]",
                "logo_b.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_b.jpg")
        );

        MockMultipartFile reviewCreateRequest = new MockMultipartFile(
                "reviewCreateRequest",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                new ObjectMapper().writeValueAsString(new ReviewCreateRequest(5, "리뷰 제목", "리뷰 내용", 1L, 1L)).getBytes()
        );

        // when & then
        mockMvc.perform(
                        multipart("/api/v1/reviews/")
                                .file(image1)
                                .file(reviewCreateRequest)
//                                .file("reviewImages[0]", image1.getBytes())
//                                .file("reviewImages[1]", image1.getBytes())
//                                .file("reviewCreateRequest", reviewCreateRequest.getBytes())
                                //  Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/octet-stream' not supported]
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                                .param("rating", "5")
//                                .param("title", "리뷰 제목")
//                                .param("content", "리뷰 내용")
//                                .param("travelProductId", "1")
//                                .param("customerId", "1")
//                                .header("Authorization", BEARER_TOKEN_TYPE + SAMPLE_TOKEN)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/review/1"));
    }

    @Test
    void 상품에_달려있는_리뷰를_조회한댜() throws Exception {
        // given
        given(reviewService.getWidgetReviewResponsesByPartnerDomainAndTravelProductIdWithCondition(any(), any(), any(), any(), any(), any())).willReturn(Collections.emptyList());

        // when & then
        mockMvc.perform(
                        get("/api/v1/reviews/products/{travelProductId}", 1)
                                .param("travelProductId", "1")
                                .param("property", "KINDNESS")
                                .param("keyword", "먼지")
                                .param("orderBy", "POSITIVE")
                                .param("page", "0")
                                .param("size", "10")
//                                .header("Authorization", BEARER_TOKEN_TYPE + SAMPLE_TOKEN)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 리뷰를_수정한다() throws Exception {
        // given
        doNothing().when(reviewService).updateById(anyLong(), any(), any());

        MockMultipartFile image2 = new MockMultipartFile(
                "reviewImages[0]",
                "logo_w.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_w.jpg")
        );

        MockMultipartFile reviewUpdateRequest = new MockMultipartFile(
                "reviewUpdateRequest",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                new ObjectMapper().writeValueAsString(new ReviewUpdateRequest(5, "리뷰 제목", "리뷰 내용")).getBytes()
        );

        // when & then
        mockMvc.perform(
                        multipart(HttpMethod.PATCH, "/api/v1/reviews/{reviewId}", 1)
                                .file(image2)
                                .file(reviewUpdateRequest)
//                                .header("Authorization", BEARER_TOKEN_TYPE + SAMPLE_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void 리뷰를_삭제한다() throws Exception {
        // given
        doNothing().when(reviewService).deleteById(anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/v1/reviews/{reviewId}", 1)
        )
        .andExpect(status().isNoContent());
    }
}