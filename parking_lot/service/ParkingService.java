package parking_lot.service;

import parking_lot.entity.ParkingLot;
import parking_lot.entity.PaymentResult;
import parking_lot.entity.Spot;
import parking_lot.entity.Ticket;
import parking_lot.entity.vehicles.Vehicle;
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
        ticket.markExit ();

        double amount = pricingStrategy.calculatePrice (ticket);
        PaymentResult paymentResult = paymentService.processPayment (amount);

        ticket.markPaid ();
        ticket.getSpot ().vacate ();
        ticket.close ();

        return paymentResult;
    }
}