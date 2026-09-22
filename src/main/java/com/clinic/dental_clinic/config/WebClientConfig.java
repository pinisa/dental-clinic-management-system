package com.clinic.dental_clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient smsWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.sms-provider-mock.com")
                .build();
    }
}