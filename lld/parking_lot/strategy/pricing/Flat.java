package lld.parking_lot.strategy.pricing;

import lld.parking_lot.entity.Ticket;

public class Flat implements PricingStrategy {
    private final double flatPrice;

    public Flat (double flatPrice) {
        this.flatPrice = flatPrice;
    }

    @Override 
    public double calculatePrice (Ticket ticket) {
        return flatPrice;
    }
}
