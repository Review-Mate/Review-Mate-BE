package com.somartreview.reviewmate.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.domain.product.SingleTravelProductCategory;
import com.somartreview.reviewmate.dto.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.product.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.reservation.SingleTravelReservationCreateRequest;
import com.somartreview.reviewmate.service.CustomerService;
import com.somartreview.reviewmate.service.ReservationService;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private SingleTravelProductService singleTravelProductService;


    @Test
    void 예약을_생성한다() throws Exception {
        // given
        given(reservationService.createSingleTravelProductReservation(any(), any(), any())).willReturn(1L);

        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("CUSTOMER_0001", "권순찬", "01012345678", "sckwon770");
        SingleTravelProductCreateRequest ProductCreateRequest = new SingleTravelProductCreateRequest("PRODUCT_0001", "신라더스테이 호텔", SingleTravelProductCategory.ACCOMMODATION, 1L);
        SingleTravelReservationCreateRequest ReservationCreateRequest = new SingleTravelReservationCreateRequest("RESERVATION_0001", LocalDateTime.parse("2023-10-18T13:00:00"), LocalDateTime.parse("2023-10-19T12:00:00"), customerCreateRequest, ProductCreateRequest);

        MockMultipartFile singleTravelReservationCreateRequest = new MockMultipartFile(
                "singleTravelReservationCreateRequest",
                "singleTravelReservationCreateRequest",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(ReservationCreateRequest).getBytes()
        );

        MockMultipartFile thumbnailFile = new MockMultipartFile(
                "thumbnailFile",
                "logo_w.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_w.jpg")
        );

        // when & then
        mockMvc.perform(
                multipart("/api/client/v1/{partnerDomain}/products/travel/single/reservations", "goodchoice.kr")
                        .file(singleTravelReservationCreateRequest)
                        .file(thumbnailFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/console/v1/products/travel/single/reservations/1")
        );
    }

    @Test
    void 예약_ID로_예약을_조회한다() throws Exception {
        // given
        given(reservationService.createSingleTravelProductReservation(any(), any(), any())).willReturn(1L);

        // when & then
        mockMvc.perform(
                get("/api/console/v1/products/travel/single/reservations/{reservationId}", 1L)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 고객_ID_혹은_여행상품_ID로_예약을_조회한다() throws Exception {
        // given
        given(reservationService.getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(anyLong(), anyLong())).willReturn(Collections.emptyList());

        // when & then
        mockMvc.perform(
                get("/api/console/v1/products/travel/single/reservations")
                        .param("customerId", "1")
                        .param("singleTravelProductId", "1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void 예약_ID로_예약을_삭제한다() throws Exception {
        // given
        doNothing().when(reservationService).deleteByReservationId(anyLong());

        // when & then
        mockMvc.perform(
                delete("/api/console/products/travel/single/reservations/{reservationId}", 1L)
                )
                .andExpect(status().isNoContent()
        );
    }
}