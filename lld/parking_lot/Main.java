package lld.parking_lot;

import java.util.List;

import lld.parking_lot.entity.Floor;
import lld.parking_lot.entity.ParkingLot;
import lld.parking_lot.entity.PaymentResult;
import lld.parking_lot.entity.Spot;
import lld.parking_lot.entity.Ticket;
import lld.parking_lot.entity.vehicles.Bike;
import lld.parking_lot.entity.vehicles.Car;
import lld.parking_lot.entity.vehicles.Vehicle;
import lld.parking_lot.enums.SpotType;
import lld.parking_lot.exception.NoSpotAvailableException;
import lld.parking_lot.service.ParkingService;
import lld.parking_lot.service.PaymentService;
import lld.parking_lot.strategy.allocation.FirstAvailable;
import lld.parking_lot.strategy.payment_processor.UpiProcessor;
import lld.parking_lot.strategy.pricing.Hybrid;

public class Main {
    private static ParkingLot buildLot () {
        List <Spot> groundFloorSpots = List.of (
            new Spot ("F-0", SpotType.BIKE),
            new Spot ("F-0", SpotType.COMPACT),
            new Spot ("F-0", SpotType.LARGE)
        ); 

        List <Spot> firstFloorSpots = List.of (
            new Spot ("F-1", SpotType.BIKE),
            new Spot ("F-1", SpotType.COMPACT),
            new Spot ("F-1", SpotType.LARGE),
            new Spot ("F-1", SpotType.COMPACT)
        );

        Floor groundFloor = new Floor ("F-0", groundFloorSpots);
        Floor firstFloor = new Floor ("F-1", firstFloorSpots);

        return new ParkingLot ("LOT-1", List.of (groundFloor, firstFloor));
    }

    public static void main (String[] args) {
        ParkingLot lot = buildLot ();

        PaymentService paymentService = new PaymentService (new UpiProcessor ());
        ParkingService parkingService = new ParkingService (
            lot, new Hybrid (20, 30), 
            new FirstAvailable (), paymentService
        );

        // 1. Happy Path
        Vehicle car = new Car ("JH-10-M-4232");
        Ticket ticket = parkingService.enterVehicle (car);

        System.out.println ("Parked at spot: " + ticket.getSpot ().getSpotId ());

        PaymentResult paymentResult = parkingService.exitVehicle (ticket);
        System.out.println ("Payment: " + paymentResult.getPaymentStatus () + "\ntxn_id: " + paymentResult.getTransactionId ().orElse ("N/A"));

        System.out.println ("-----");

        // 2. Lot full: there's only one bike spot
        parkingService.enterVehicle (new Bike ("KA-02-1111"));
        parkingService.enterVehicle (new Bike ("KA-02-2222"));
        try {
            parkingService.enterVehicle (new Bike ("KA-03-3333"));
        } catch (NoSpotAvailableException e) {
            System.out.println ("[Error] : " + e.getMessage ());
        }

        System.out.println ("-----");

        // 3. Invalid state transition: exiting with the same ticket twice
        try {
            parkingService.exitVehicle (ticket);
        } catch (IllegalStateException e) {
            System.out.println ("[Error] : " + e.getMessage ());
        }
    }
}
