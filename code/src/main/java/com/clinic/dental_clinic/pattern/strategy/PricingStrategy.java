package com.clinic.dental_clinic.pattern.strategy;

public interface PricingStrategy {
    double calculateFinalPrice(double basePrice);
}