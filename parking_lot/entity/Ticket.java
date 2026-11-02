package parking_lot.entity;

import java.time.LocalDateTime;

import parking_lot.entity.vehicles.Vehicle;
import parking_lot.enums.TicketStatus;

public class Ticket {
    String ticketId;
    Vehicle vehicle;
    Spot spot;
    LocalDateTime entryTime;
    LocalDateTime exitTime;
    TicketStatus ticketStatus;
}
