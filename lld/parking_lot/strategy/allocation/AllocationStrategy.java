package lld.parking_lot.strategy.allocation;

import java.util.Optional;

import lld.parking_lot.entity.ParkingLot;
import lld.parking_lot.entity.Spot;
import lld.parking_lot.entity.vehicles.Vehicle;

public interface AllocationStrategy {
    Optional <Spot> findSpot (Vehicle vehicle, ParkingLot parkingLot);
}
