package parking_lot.strategy.allocation;

import java.util.List;
import java.util.Optional;

import parking_lot.entity.Floor;
import parking_lot.entity.ParkingLot;
import parking_lot.entity.Spot;
import parking_lot.entity.vehicles.Vehicle;

public class FirstAvailable implements AllocationStrategy {
    @Override
    public Optional <Spot> findSpot (Vehicle vehicle, ParkingLot parkingLot) {
        List <Floor> floors = parkingLot.getFloors ();
        for (Floor floor : floors) {
            List <Spot> spots = floor.getSpots ();
            for (Spot spot : spots) {
                if (spot.isAvailable () && spot.canFit (vehicle)) 
                    return Optional.of (spot);
            }
        }

        return Optional.empty ();
    }
}
