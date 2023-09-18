package com.somartreview.reviewmate.web.partners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerCreateRequest;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerResponse;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.service.partners.PartnerManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartnerManagerController.class)
class PartnerManagerControllerTest {

    private final String PARTNER_DOMAIN = "goodchoice.kr";


    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartnerManagerService partnerManagerService;


    @Test
    void 파트너사의_관리자를_생성한다() throws Exception {
        // given
        given(partnerManagerService.create(any())).willReturn(1L);
        PartnerManagerCreateRequest partnerManagerCreateRequest = new PartnerManagerCreateRequest("권순찬", "sckwon770@gmail.com", "password1234", PARTNER_DOMAIN);

        // when & then
        mockMvc.perform(
                        post("/api/console/v1/managers/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(partnerManagerCreateRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/console/managers/1"));
    }

    @Test
    void 파트너사의_관리자를_조회한다() throws Exception {
        // given
        given(partnerManagerService.getPartnerManagerResponseById(anyLong())).willReturn(new PartnerManagerResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/managers/{partnerManagerId}", 1L)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 파트너사의_관리자_정보를_수정한다() throws Exception {
        // given
        doNothing().when(partnerManagerService).updateById(anyLong(), any());
        PartnerManagerUpdateRequest partnerManagerUpdateRequest = new PartnerManagerUpdateRequest("권순찬", "sckwon770@gmail.com", "password1234");

        // when & then
        mockMvc.perform(
                        put("/api/console/v1/managers/{partnerManagerId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(partnerManagerUpdateRequest))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void 파트너사의_관리자를_삭제한다() throws Exception {
        // given
        doNothing().when(partnerManagerService).deleteById(anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/console/v1/managers/{partnerManagerId}", 1L)
                )
                .andExpect(status().isNoContent());
    }
}