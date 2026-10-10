package com.clinic.dental_clinic.pattern.strategy;

public interface PricingStrategy {
    Double calculatePrice(Double basePrice);
}