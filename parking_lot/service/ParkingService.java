package parking_lot.service;

import parking_lot.entity.ParkingLot;
import parking_lot.entity.PaymentResult;
import parking_lot.entity.Spot;
import parking_lot.entity.Ticket;
import parking_lot.entity.vehicles.Vehicle;
import parking_lot.enums.PaymentStatus;
import parking_lot.exception.NoSpotAvailableException;
import parking_lot.strategy.allocation.AllocationStrategy;
import parking_lot.strategy.pricing.PricingStrategy;

public class ParkingService {
    private ParkingLot parkingLot;
    private PricingStrategy pricingStrategy;
    private AllocationStrategy allocationStrategy;
    private PaymentService paymentService;

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
        Spot spot = allocationStrategy.findSpot (vehicle, parkingLot)
                    .orElseThrow (() -> new NoSpotAvailableException(vehicle.getType ()));
        
        spot.occupy (vehicle);
        return new Ticket (vehicle, spot);
    } 

    public synchronized PaymentResult exitVehicle (Ticket ticket) {
        // Stamp the exit time and move ACTIVE -> PAYMENT_PENDING, so the
        // pricing strategy has a fixed duration to charge for.
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

        return paymentResult;
    }
}