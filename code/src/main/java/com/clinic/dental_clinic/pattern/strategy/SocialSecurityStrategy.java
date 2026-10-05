package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.stereotype.Component;

@Component("SOCIAL_SECURITY")
public class SocialSecurityStrategy implements PricingStrategy {

    @Override
    public double calculateFinalPrice(double basePrice) {
        // ประกันสังคม: หักส่วนลดสูงสุด 900 บาท
        return Math.max(0.0, basePrice - 900.0);
    }
}