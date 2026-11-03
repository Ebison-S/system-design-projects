package parking_lot.strategy.allocation;

import java.util.Optional;

import parking_lot.entity.ParkingLot;
import parking_lot.entity.Spot;
import parking_lot.entity.vehicles.Vehicle;

public interface AllocationStrategy {
    Optional <Spot> findSpot (Vehicle vehicle, ParkingLot parkingLot);
}
