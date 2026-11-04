package parking_lot.strategy.pricing;

import parking_lot.entity.Ticket;

public class Hybrid implements PricingStrategy {
    private final double flatPrice;
    private final double hourlyRate;

    public Hybrid (double flatPrice, double hourlyRate) {
        this.flatPrice = flatPrice;
        this.hourlyRate = hourlyRate;
    }

    @Override 
    public double calculatePrice (Ticket ticket) {
        long minutes = ticket.getDuration ().toMinutes ();
        long hours = Math.max (1, (long) Math.ceil (minutes / 60.0));

        return flatPrice + (hours - 1) * hourlyRate;
    }
}
