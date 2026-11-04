package parking_lot.strategy.pricing;

import parking_lot.entity.Ticket;

public interface PricingStrategy {
    double calculatePrice (Ticket ticket);
}
