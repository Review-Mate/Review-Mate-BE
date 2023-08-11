package com.somartreview.reviewmate.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 리뷰를_생성한다() throws Exception {
        // given
        MockMultipartFile image1 = new MockMultipartFile(
                "logo_b",
                "logo_b.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_b.jpg")
        );

        MockMultipartFile image2 = new MockMultipartFile(
                "logo_w",
                "logo_w.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_w.jpg")
        );

        // when
        mockMvc.perform(
                        multipart("/api/v1/review/")
                                .file("reviewImages[0]", image1.getBytes())
                                .file("reviewImages[1]", image1.getBytes())
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
    }
}