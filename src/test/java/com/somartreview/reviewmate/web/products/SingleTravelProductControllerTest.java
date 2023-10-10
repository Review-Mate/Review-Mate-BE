package com.somartreview.reviewmate.web.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.dto.product.SingleTravelProductConsoleElementResponse;
import com.somartreview.reviewmate.dto.product.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import com.somartreview.reviewmate.service.products.TravelProductDeleteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.util.Collections;

import static com.somartreview.reviewmate.domain.product.SingleTravelProductCategory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SingleTravelProductController.class)
class SingleTravelProductControllerTest {

    private final String PARTNER_DOMAIN = "goodchoice.kr";


    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SingleTravelProductService singleTravelProductService;

    @MockBean
    private TravelProductDeleteService travelProductDeleteService;


    @Test
    void 여행상품_ID로_단일_여행상품을_조회한다() throws Exception {
        // given
        given(singleTravelProductService.getSingleTravelProductConsoleElementResponseById(anyLong())).willReturn(new SingleTravelProductConsoleElementResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/products/travel/single/{travelProductId}", 1L)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 파트너의_커스텀_ID로_단일_여행상품을_조회한다() throws Exception {
        // given
        given(singleTravelProductService.getSingleTravelProductConsoleElementResponseByPartnerCustomId(anyString(), anyString())).willReturn(new SingleTravelProductConsoleElementResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/{partnerDomain}/products/travel/single/{partnerCustomId}", PARTNER_DOMAIN, "PRODUCT-0001")
                )
                .andExpect(status().isOk());
    }

    @Test
    void 카테고리에_속한_단일_여행상품들을_조회한다() throws Exception {
        // given
        given(singleTravelProductService.getSingleTravelProductConsoleElementResponsesByPartnerDomainAndTravelProductCategory(anyString(), any())).willReturn(Collections.emptyList());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/{partnerDomain}/products/travel/single", PARTNER_DOMAIN)
                                .param("singleTravelProductCategory", String.valueOf(ACCOMMODATION))
                )
                .andExpect(status().isOk());
    }

    @Test
    void 파트너ID로_단일_여행상품의_정보를_수정한다() throws Exception {
        // given
        doNothing().when(singleTravelProductService).update(anyString(), anyString(), any(), any());

        MockMultipartFile singleTravelProductUpdateRequest = new MockMultipartFile(
                "singleTravelProductUpdateRequest",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(new SingleTravelProductUpdateRequest("신라더스테이 호텔", ACCOMMODATION)).getBytes()
        );

        MockMultipartFile thumbnailFile = new MockMultipartFile(
                "thumbnailFile",
                "logo_w.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_w.jpg")
        );

        // when & then
        mockMvc.perform(
                        multipart(HttpMethod.PATCH, "/api/console/v1/{partnerDomain}/products/travel/single/{partnerCustomId}", PARTNER_DOMAIN, "PRODUCT-0001")
                                .file(singleTravelProductUpdateRequest)
                                .file(thumbnailFile)
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void 여행상품_ID로_단일_여행상품을_삭제한다() throws Exception {
        // given
        doNothing().when(travelProductDeleteService).delete(anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/console/v1/products/travel/single/{travelProductId}", 1L)
                )
                .andExpect(status().isNoContent());
    }
}