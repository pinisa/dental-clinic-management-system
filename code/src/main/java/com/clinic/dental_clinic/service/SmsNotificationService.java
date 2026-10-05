package com.clinic.dental_clinic.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Scope("singleton")
public class SmsNotificationService {

    private final WebClient smsWebClient;

    public SmsNotificationService(WebClient smsWebClient) {
        this.smsWebClient = smsWebClient;
    }

    public void sendQueueNotification(String phone, String messageContent) {
        smsWebClient.post()
                .uri("/send-sms")
                .bodyValue(Map.of("phone", phone, "message", messageContent))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("SMS Gateway Fallback Response")
                .subscribe(response -> System.out.println("SMS WebClient Result: " + response));
    }
}