package com.somartreview.reviewmate.web;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class})
public class ControllerTest {

    protected MockMvc mockMvc;
    protected MockMultipartFile testImage1;
    protected MockMultipartFile testImage2;

    @BeforeEach
    protected void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        this.testImage1 = new MockMultipartFile(
                "logo_b",
                "logo_b.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_b.jpg")
        );

        this.testImage2 = new MockMultipartFile(
                "logo_w",
                "logo_w.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream("src/test/resources/images/logo_w.jpg")
        );
    }
}
