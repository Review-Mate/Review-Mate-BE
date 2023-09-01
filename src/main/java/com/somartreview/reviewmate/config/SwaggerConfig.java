package com.somartreview.reviewmate.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("v1") String appVersion) {
        Info info = new Info()
                .title("리뷰메이트 앱서버 Private API")
                .description("리뷰메이트 서비스 어플리케이션을 위한 서버의 private API 문서입니다.")
                .version(appVersion);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
