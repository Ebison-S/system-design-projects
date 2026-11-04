package parking_lot;

import java.util.List;

import parking_lot.entity.Floor;
import parking_lot.entity.ParkingLot;
import parking_lot.entity.PaymentResult;
import parking_lot.entity.Spot;
import parking_lot.entity.Ticket;
import parking_lot.entity.vehicles.Car;
import parking_lot.entity.vehicles.Vehicle;
import parking_lot.enums.SpotType;
import parking_lot.service.ParkingService;
import parking_lot.service.PaymentService;
import parking_lot.strategy.allocation.FirstAvailable;
import parking_lot.strategy.payment_processor.UpiProcessor;
import parking_lot.strategy.pricing.Hybrid;

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

        Floor groundFloor = new Floor (groundFloorSpots);
        Floor firstFloor = new Floor (firstFloorSpots);

        return new ParkingLot (List.of (groundFloor, firstFloor));
    }

    public static void main (String[] args) {
        ParkingLot lot = buildLot ();

        PaymentService paymentService = new PaymentService (new UpiProcessor ());
        ParkingService parkingService = new ParkingService (
            lot, new Hybrid (20, 30), 
            new FirstAvailable (), paymentService
        );

        Vehicle car = new Car ("JH-10-M-4232");
        Ticket ticket = parkingService.enterVehicle (car);

        System.out.println ("Parked at spot: " + ticket.getSpot ().getSpotId ());

        PaymentResult paymentResult = parkingService.exitVehicle (ticket);
        System.out.println ("Payment: " + paymentResult.getPaymentStatus () + "\ntxn_id: " + paymentResult.getTransactionId ());
    }
}
