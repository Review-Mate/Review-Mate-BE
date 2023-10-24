package com.somartreview.reviewmate.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.dto.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.customer.CustomerResponse;
import com.somartreview.reviewmate.dto.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.service.CustomerDeleteService;
import com.somartreview.reviewmate.service.CustomerService;
import com.somartreview.reviewmate.service.partners.company.PartnerCompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private final String PARTNER_DOMAIN = "goodchoice.kr";


    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerDeleteService customerDeleteService;

    @MockBean
    private PartnerCompanyService partnerCompanyService;


    @Test
    void 고객을_생성한다() throws Exception {
        // given
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);
        given(customerService.create(anyString(), any())).willReturn(mockCustomer);
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("CUSTOMER-0001", "권순찬", "01012345678", "sckwon770");

        // when & then
        mockMvc.perform(
                        post("/api/console/v1/{partnerDomain}/customers", PARTNER_DOMAIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString((customerCreateRequest)))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/console/v1/customers/1"));
    }

    @Test
    void 파트너의_커스텀_ID로_고객을_조회한다() throws Exception {
        // given
        given(customerService.getCustomerResponseByPartnerDomainAndPartnerCustomId(anyString(), anyString())).willReturn(new CustomerResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/{partnerDomain}/customers/{partnerCustomId}", PARTNER_DOMAIN, "CUSTOMER-0001")
                )
                .andExpect(status().isOk());
    }

    @Test
    void 고객_ID로_고객을_조회한다() throws Exception {
        // given
        given(customerService.getCustomerResponseById(anyLong())).willReturn(new CustomerResponse());

        // when & then
        mockMvc.perform(
                        get("/api/console/v1/customers/{customerId}", 1L)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 파트너의_커스텀_ID로_고객의_정보를_수정한다() throws Exception {
        // given
        doNothing().when(customerService).update(anyString(), anyString(), any());
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("권순찬", "01012345678", "sckwon770");

        // when & then
        mockMvc.perform(
                put("/api/console/v1/{partnerDomain}/customers/{partnerCustomId}", PARTNER_DOMAIN, "CUSTOMER-0001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerUpdateRequest))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void 고객_ID로_고객의_정보를_수정한다() throws Exception {
        // given
        doNothing().when(customerService).update(anyString(), anyString(), any());
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("권순찬", "01012345678", "sckwon770");

        // when & then
        mockMvc.perform(
                put("/api/console/v1/customers/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerUpdateRequest))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void 파트너_커스텀_ID로_고객을_삭제한다() throws Exception {
        // given
        doNothing().when(customerDeleteService).deleteByPartnerDomainAndPartnerCustomId(anyString(), anyString());

        // when & then
        mockMvc.perform(
                delete("/api/console/v1/{partnerDomain}/customers/{partnerCustomId}", PARTNER_DOMAIN, "CUSTOMER-0001")
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void 고객_ID로_고객을_삭제한다() throws Exception {
        // given
        doNothing().when(customerDeleteService).delete(anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/console/v1/customers/{customerId}", 1L)
                )
                .andExpect(status().isNoContent());
    }
}