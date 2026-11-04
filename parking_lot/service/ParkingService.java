package parking_lot.service;

import java.util.HashMap;
import java.util.Map;

import parking_lot.entity.ParkingLot;
import parking_lot.entity.PaymentResult;
import parking_lot.entity.Spot;
import parking_lot.entity.Ticket;
import parking_lot.entity.vehicles.Vehicle;
import parking_lot.enums.PaymentStatus;
import parking_lot.enums.TicketStatus;
import parking_lot.exception.NoSpotAvailableException;
import parking_lot.exception.VehicleAlreadyParkedException;
import parking_lot.strategy.allocation.AllocationStrategy;
import parking_lot.strategy.pricing.PricingStrategy;

public class ParkingService {
    private ParkingLot parkingLot;
    private PricingStrategy pricingStrategy;
    private AllocationStrategy allocationStrategy;
    private PaymentService paymentService;

    // Open tickets keyed by vehicle number, used to block duplicate entries.
    private final Map <String, Ticket> activeTickets = new HashMap <> ();

    public ParkingService (
        ParkingLot parkingLot,
        PricingStrategy pricingStrategy,
        AllocationStrategy allocationStrategy,
        PaymentService paymentService
    ) {
        this.parkingLot = parkingLot;
        this.pricingStrategy = pricingStrategy;
        this.allocationStrategy = allocationStrategy;
        this.paymentService = paymentService;
    }

    public synchronized Ticket enterVehicle (Vehicle vehicle) {
        // A vehicle with an open ticket is still inside the lot; letting it
        // enter again would take a second spot for the same vehicle.
        if (activeTickets.containsKey (vehicle.getVehicleNumber ()))
            throw new VehicleAlreadyParkedException (vehicle.getVehicleNumber ());

        Spot spot = allocationStrategy.findSpot (vehicle, parkingLot)
                    .orElseThrow (() -> new NoSpotAvailableException(vehicle.getType ()));

        spot.occupy (vehicle);
        Ticket ticket = new Ticket (vehicle, spot);
        activeTickets.put (vehicle.getVehicleNumber (), ticket);
        return ticket;
    }

    public synchronized PaymentResult exitVehicle (Ticket ticket) {
        // Stamp the exit time and move ACTIVE -> PAYMENT_PENDING, so the
        // pricing strategy has a fixed duration to charge for.
        // On a retry after a failed payment the ticket is already
        // PAYMENT_PENDING with its exit time set, so skip this step and
        // charge the same amount again instead of failing the transition.
        // Any other status (e.g. CLOSED) still goes through markExit (), so
        // the enum rejects it before any money is charged.
        if (ticket.getStatus () != TicketStatus.PAYMENT_PENDING)
            ticket.markExit ();

        double amount = pricingStrategy.calculatePrice (ticket);
        PaymentResult paymentResult = paymentService.processPayment (amount);

        // Only finish the exit if the money actually came through. On a failed
        // payment, return early so that:
        //   - the ticket stays PAYMENT_PENDING (not wrongly marked PAID),
        //   - the spot stays OCCUPIED (the vehicle hasn't left yet),
        //   - the caller gets the FAILED result and can decide what to do.
        if (paymentResult.getPaymentStatus () != PaymentStatus.SUCCESS)
            return paymentResult;

        // Payment succeeded: PAYMENT_PENDING -> PAID, free the spot, then
        // PAID -> CLOSED. Each step is validated by the status enums.
        ticket.markPaid ();
        ticket.getSpot ().vacate ();
        ticket.close ();

        // The vehicle has left, so it may enter again later.
        activeTickets.remove (ticket.getVehicle ().getVehicleNumber ());

        return paymentResult;
    }
}