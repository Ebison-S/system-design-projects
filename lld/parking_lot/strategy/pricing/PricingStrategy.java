package lld.parking_lot.strategy.pricing;

import lld.parking_lot.entity.Ticket;

public interface PricingStrategy {
    double calculatePrice (Ticket ticket);
}
