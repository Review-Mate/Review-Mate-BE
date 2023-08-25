package com.somartreview.reviewmate.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somartreview.reviewmate.service.review.ReviewService;
import com.somartreview.reviewmate.web.review.ReviewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        TestController.class,
        ReviewController.class
})
public abstract class ControllerTest {

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ReviewService reviewService;
}
