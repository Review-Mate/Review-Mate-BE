package com.somartreview.reviewmate.web.partners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyCreateRequest;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyResponse;
import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.service.partners.company.PartnerCompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartnerCompanyController.class)
class PartnerCompanyControllerTest {

    private final String PARTNER_DOMAIN = "goodchoice.kr";

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartnerCompanyService partnerCompanyService;


    @Test
    void 파트너사를_생성한다() throws Exception {
        // given
        given(partnerCompanyService.create(any())).willReturn(PARTNER_DOMAIN);
        PartnerCompanyCreateRequest partnerCompanyCreateRequest = new PartnerCompanyCreateRequest("goodchoice.kr", "여기어때컴퍼니");

        // when & then
        mockMvc.perform(
                        post("/api/console/v1/companies/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(partnerCompanyCreateRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/console/v1/companies/" + PARTNER_DOMAIN));
    }

    @Test
    void 파트너사를_조회한다() throws Exception {
        // given
        given(partnerCompanyService.getPartnerCompanyResponseByPartnerDomain(anyString())).willReturn(new PartnerCompanyResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/companies/" + PARTNER_DOMAIN)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 파트너사의_정보를_수정한다() throws Exception {
        // given
        doNothing().when(partnerCompanyService).update(anyString(), any());
        PartnerCompanyUpdateRequest partnerCompanyUpdateRequest = new PartnerCompanyUpdateRequest(PARTNER_DOMAIN, "여기어때컴퍼니");

        // when & then
        mockMvc.perform(
                        put("/api/console/v1/companies/{partnerDomain}", PARTNER_DOMAIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(partnerCompanyUpdateRequest))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void 파트너사를_삭제한다() throws Exception {
        // given
        doNothing().when(partnerCompanyService).delete(anyString());

        // when & then
        mockMvc.perform(
                        delete("/api/console/v1/companies/{partnerDomain}", PARTNER_DOMAIN)
                )
                .andExpect(status().isNoContent());
    }
}