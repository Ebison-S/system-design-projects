package parking_lot.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import parking_lot.entity.vehicles.Vehicle;
import parking_lot.enums.TicketStatus;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final Spot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private TicketStatus ticketStatus;

    public Ticket (Vehicle vehicle,Spot spot) {
        this.ticketId = UUID.randomUUID ().toString ();
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now ();
        this.ticketStatus = TicketStatus.ACTIVE;
    }

    public void markExit () {
        transitionTo (TicketStatus.PAYMENT_PENDING);
        this.exitTime = LocalDateTime.now ();
    }

    public void markPaid () {
        transitionTo (TicketStatus.PAID);
    }

    public void close () {
        transitionTo (TicketStatus.CLOSED);
    }

    private void transitionTo (TicketStatus next) {
        if (!ticketStatus.canTransitionTo (next))
            throw new IllegalStateException (
                "Ticket " + ticketId + " cannot move from " + ticketStatus + " to " + next);

        this.ticketStatus = next;
    }

    public Spot getSpot () { return spot; }

    public Vehicle getVehicle () { return vehicle; }

    public TicketStatus getStatus () { return ticketStatus; }

    public Duration getDuration () {
        if (exitTime == null)
            throw new IllegalStateException ("Ticket " + ticketId + " has no exit time yet");

        return Duration.between (entryTime, exitTime);
    }
}
