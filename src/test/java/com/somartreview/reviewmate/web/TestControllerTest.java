package com.somartreview.reviewmate.web;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.somartreview.reviewmate.web.ApiDocumentUtils.getDocumentRequest;
import static com.somartreview.reviewmate.web.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
public class TestControllerTest extends ControllerTest {

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
                                        .tag("Greet")
                                        .description("서버 상태를 확인"),
//                                        .requestSchema(Schema.schema("ResponseEntity?.PUT"))
//                                        .responseSchema(Schema.schema("ResponseEntity?.POST")))
                                getDocumentRequest(),
                                getDocumentResponse()
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
//                                responseFields(
//                                        fieldWithPath("hello").type(STRING).description("상태")
//                                )
                        )
                );

    }
}
