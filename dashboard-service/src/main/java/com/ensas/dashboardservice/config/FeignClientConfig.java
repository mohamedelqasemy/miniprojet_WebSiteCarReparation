package com.ensas.dashboardservice.config;

import com.ensas.dashboardservice.service.KeycloakTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig implements RequestInterceptor {

    private final KeycloakTokenProvider tokenProvider;

    public FeignClientConfig(KeycloakTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = tokenProvider.getToken();
        template.header("Authorization", "Bearer " + token);
    }
}

