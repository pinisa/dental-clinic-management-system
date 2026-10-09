package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.stereotype.Component;

@Component("SOCIAL_SECURITY")
public class SocialSecurityStrategy implements PricingStrategy {
    @Override
    public Double calculatePrice(Double basePrice) {
        return basePrice * 0.90;
    }
}