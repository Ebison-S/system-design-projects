package parking_lot.strategy.pricing;

import parking_lot.entity.Ticket;

public class TimeBased implements PricingStrategy {
    private final double hourlyRate;

    public TimeBased (double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override 
    public double calculatePrice (Ticket ticket) {
        long minutes = ticket.getDuration ().toMinutes ();
        long hours = Math.max (1, (long) Math.ceil (minutes / 60.0));

        return hours * hourlyRate;
    }
}
