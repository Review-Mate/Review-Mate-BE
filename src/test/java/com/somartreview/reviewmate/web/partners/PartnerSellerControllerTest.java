package com.somartreview.reviewmate.web.partners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerCreateRequest;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerResponse;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.service.partners.PartnerSellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartnerSellerController.class)
class PartnerSellerControllerTest {

    private final String PARTNER_DOMAIN = "goodchoice.kr";


    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartnerSellerService partnerSellerService;


    @Test
    void 파트너사의_판매자를_생성한다() throws Exception {
        // given
        given(partnerSellerService.create(any())).willReturn(1L);
        PartnerSellerCreateRequest partnerSellerCreateRequest = new PartnerSellerCreateRequest("권순찬", "01012345678", "sckwon770", PARTNER_DOMAIN);

        // when & then
        mockMvc.perform(
                        post("/api/console/v1/sellers/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(partnerSellerCreateRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/console/v1/sellers/1"));
    }

    @Test
    void 파트너사의_판매자를_조회한다() throws Exception {
        // given
        given(partnerSellerService.getPartnerSellerResponseById(any())).willReturn(new PartnerSellerResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/sellers/{partnerSellerId}", 1)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 파트너사의_판매자_정보를_수정한다() throws Exception {
        // given
        doNothing().when(partnerSellerService).updateById(any(), any());
        PartnerSellerUpdateRequest partnerSellerUpdateRequest = new PartnerSellerUpdateRequest("권순찬", "01012345678", "sckwon770");

        // when & then
        mockMvc.perform(
                        put("/api/console/v1/sellers/{partnerSellerId}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(partnerSellerUpdateRequest))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void 파트너사의_판매자를_삭제한다() throws Exception {
        // given
        doNothing().when(partnerSellerService).deleteById(any());

        // when & then
        mockMvc.perform(
                        delete("/api/console/v1/sellers/{partnerSellerId}", 1)
                )
                .andExpect(status().isNoContent());
    }
}