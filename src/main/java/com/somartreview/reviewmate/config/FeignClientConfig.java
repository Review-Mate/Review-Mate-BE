package com.somartreview.reviewmate.config;

import feign.Request;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "com.somartreview.reviewmate.domain")
public class FeignClientConfig {

    public Request.Options feignClientConfigurer() {

        final long CONNECT_TIME_OUT = 10000;
        final long READ_TIME_OUT = 10000;

        return new Request.Options(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS, READ_TIME_OUT, TimeUnit.MILLISECONDS, false);
    }
}
