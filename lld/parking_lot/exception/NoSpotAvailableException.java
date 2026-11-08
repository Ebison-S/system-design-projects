package lld.parking_lot.exception;

import lld.parking_lot.enums.VehicleType;

public class NoSpotAvailableException extends RuntimeException {
    public NoSpotAvailableException (VehicleType vehicleType) {
        super ("No available spot for vehicle type: " + vehicleType);
    }
}
