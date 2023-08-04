package com.somartreview.reviewmate.web;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class TestControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void 서버를_확인한다() throws Exception {

        mockMvc.perform(
                        get("/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .param("a", "test_hello")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
//                .andExpect(jsonPath("hello").value("hello"))
                .andDo(
                        document("greet",
                                ResourceSnippetParameters.builder()
                                        .description("서버 상태를 확인"),
//                                        .requestSchema(Schema.schema("ResponseEntity?.PUT"))
//                                        .responseSchema(Schema.schema("ResponseEntity?.POST")))
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                        parameterWithName("a").description("member")
//                                ),
//                                queryParameters(
//                                        parameterWithName("a").description("member")
//                                ),
//                                requestFields( // 파라미터 명세
//                                        fieldWithPath("a").type(STRING).description("멤버 id")
//                                ),
//                                requestParts( // 멀티파트 파일 명세
//                                        partWithName("file").description("동영상 파일")
//                                ),
                                responseFields(
                                        fieldWithPath("hello").type(STRING).description("상태")
                                )
                        )
                );

    }
}
